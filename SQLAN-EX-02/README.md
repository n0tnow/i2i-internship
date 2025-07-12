# SQL Analitik Fonksiyonları - HR Şeması Çözümleri

## 📋 İçindekiler
- [Soru 1: Departman Çalışan İsimlerini Birleştirme](#soru-1)
- [Soru 2: Job ID Gruplarında Önceki/Sonraki Maaş Toplamı](#soru-2)
- [Soru 3: Job ID Gruplarında Sonraki Çalışanın Telefonu](#soru-3)
- [Soru 4: Şirket Geneli Maaş Sıralaması](#soru-4)
- [Soru 5: Çalışanları 10'arlı Gruplara Ayırma](#soru-5)
- [Soru 6: Departman Ortalama Maaş Karşılaştırması](#soru-6) ⭐
- [Soru 7: Her Yılın İlk İşe Alınan Çalışanı](#soru-7)
- [Soru 8: Departman En Yüksek Maaş Dışındakiler](#soru-8)
- [Soru 9: Departman İlk 2 En Yüksek Maaş](#soru-9)
- [Soru 10: Departmanda Önceki/Sonraki İşe Başlayanlar](#soru-10)
- [Kullanılan Analitik Fonksiyonlar Özeti](#fonksiyonlar-özeti)

---

## Soru 1
**Bir departman'da çalışan tüm kişilerin isimlerini tek kolonda yan yana olacak şekilde yazınız.**

```sql
SELECT 
    department_id,
    LISTAGG(first_name || ' ' || last_name, ', ') 
    WITHIN GROUP (ORDER BY employee_id) AS employee_names
FROM employees
WHERE department_id IS NOT NULL
GROUP BY department_id;
```

**Kullanılan Fonksiyonlar:**
- `LISTAGG`: Değerleri birleştirip tek sütunda gösterir
- `WITHIN GROUP (ORDER BY ...)`: Birleştirme sırasını belirler

---

## Soru 2
**Job ID'ye göre gruplanacak şekilde Employee ID sıralaması baz alınarak, her çalışan için kendisinden 1 önce ve sonra çalışanların maaşlarının toplamını yazınız.**

```sql
SELECT 
    employee_id,
    first_name,
    last_name,
    job_id,
    salary,
    LAG(salary, 1) OVER (PARTITION BY job_id ORDER BY employee_id) AS previous_salary,
    LEAD(salary, 1) OVER (PARTITION BY job_id ORDER BY employee_id) AS next_salary,
    NVL(LAG(salary, 1) OVER (PARTITION BY job_id ORDER BY employee_id), 0) + 
    NVL(LEAD(salary, 1) OVER (PARTITION BY job_id ORDER BY employee_id), 0) AS total_adjacent_salaries
FROM employees
ORDER BY job_id, employee_id;
```

**Kullanılan Fonksiyonlar:**
- `LAG`: Önceki satırdaki değeri getirir
- `LEAD`: Sonraki satırdaki değeri getirir
- `NVL`: NULL değerleri 0 ile değiştirir

---

## Soru 3
**Job ID'ye göre gruplanacak şekilde Employee ID sıralaması baz alınarak, her çalışan için kendinden 1 sonra gelen çalışanın telefon numarasını yazınız.**

```sql
SELECT 
    employee_id,
    first_name,
    last_name,
    job_id,
    phone_number,
    LEAD(phone_number, 1) OVER (PARTITION BY job_id ORDER BY employee_id) AS next_employee_phone
FROM employees
ORDER BY job_id, employee_id;
```

**Kullanılan Fonksiyonlar:**
- `LEAD`: Sonraki satırdaki değeri getirir

---

## Soru 4
**Tüm çalışanların, şirketteki maaş sıralamasını yazınız. Aynı maaşa ait birden fazla kişi varsa işe giriş tarihini de sıralamaya ekleyiniz.**

```sql
SELECT 
    employee_id,
    first_name,
    last_name,
    salary,
    hire_date,
    RANK() OVER (ORDER BY salary DESC, hire_date ASC) AS salary_rank,
    DENSE_RANK() OVER (ORDER BY salary DESC, hire_date ASC) AS salary_dense_rank,
    ROW_NUMBER() OVER (ORDER BY salary DESC, hire_date ASC) AS salary_row_number
FROM employees
ORDER BY salary DESC, hire_date ASC;
```

**Kullanılan Fonksiyonlar:**
- `RANK()`: Sıralama yapar (aynı değerler için boşluk bırakır)
- `DENSE_RANK()`: Sıralama yapar (aynı değerler için boşluk bırakmaz)
- `ROW_NUMBER()`: Sıralı benzersiz numara verir

---

## Soru 5
**Tüm çalışanları, Employee ID sıralamasına göre 10'arlı gruplara ayırınız.**

```sql
SELECT 
    employee_id,
    first_name,
    last_name,
    NTILE(10) OVER (ORDER BY employee_id) AS group_number
FROM employees
ORDER BY employee_id;
```

**Kullanılan Fonksiyonlar:**
- `NTILE(n)`: Veriyi eşit n gruba böler

---

## Soru 6 ⭐
**Her departman için, o departmanın ortalama maaşın altında olan çalışanları 0; üstünde olanları ise 1 ile belirtiniz.**

```sql
SELECT 
    employee_id,
    first_name,
    last_name,
    department_id,
    salary,
    AVG(salary) OVER (PARTITION BY department_id) AS dept_avg_salary,
    CASE 
        WHEN salary >= AVG(salary) OVER (PARTITION BY department_id) THEN 1 
        ELSE 0 
    END AS above_avg_indicator
FROM employees
WHERE department_id IS NOT NULL
ORDER BY department_id, employee_id;
```

**Kullanılan Fonksiyonlar:**
- `AVG() OVER (PARTITION BY ...)`: Pencere fonksiyonu olarak her departman için ortalama maaş hesaplar
- `CASE WHEN`: Koşullu mantık ile 0/1 değeri atar
- `PARTITION BY`: Veriyi departman bazında gruplar

---

## Soru 7
**Her yıl için, işe ilk alınan çalışanı bulunuz.**

```sql
SELECT 
    employee_id,
    first_name,
    last_name,
    hire_date,
    EXTRACT(YEAR FROM hire_date) AS hire_year,
    ROW_NUMBER() OVER (PARTITION BY EXTRACT(YEAR FROM hire_date) ORDER BY hire_date) AS hire_order
FROM employees
WHERE ROW_NUMBER() OVER (PARTITION BY EXTRACT(YEAR FROM hire_date) ORDER BY hire_date) = 1
ORDER BY hire_date;
```

**Alternatif çözüm (CTE kullanarak):**
```sql
WITH yearly_first AS (
    SELECT 
        employee_id,
        first_name,
        last_name,
        hire_date,
        EXTRACT(YEAR FROM hire_date) AS hire_year,
        ROW_NUMBER() OVER (PARTITION BY EXTRACT(YEAR FROM hire_date) ORDER BY hire_date) AS rn
    FROM employees
)
SELECT 
    employee_id,
    first_name,
    last_name,
    hire_date,
    hire_year
FROM yearly_first
WHERE rn = 1
ORDER BY hire_date;
```

---

## Soru 8
**Bir departman'daki en yüksek maaş alan çalışan dışında kalanları listeleyiniz.**

```sql
SELECT 
    employee_id,
    first_name,
    last_name,
    department_id,
    salary,
    MAX(salary) OVER (PARTITION BY department_id) AS max_dept_salary
FROM employees
WHERE department_id IS NOT NULL
AND salary < MAX(salary) OVER (PARTITION BY department_id)
ORDER BY department_id, salary DESC;
```

**Kullanılan Fonksiyonlar:**
- `MAX() OVER (PARTITION BY ...)`: Departman bazında en yüksek maaşı bulur

---

## Soru 9
**Bir departman içinde en yüksek maaş alan 2 çalışanını listeleyiniz.**

```sql
SELECT 
    employee_id,
    first_name,
    last_name,
    department_id,
    salary,
    DENSE_RANK() OVER (PARTITION BY department_id ORDER BY salary DESC) AS salary_rank
FROM employees
WHERE department_id IS NOT NULL
AND DENSE_RANK() OVER (PARTITION BY department_id ORDER BY salary DESC) <= 2
ORDER BY department_id, salary DESC;
```

---

## Soru 10
**Bir departman'da bulunan tüm çalışanlar için, kendisinden önce ve sonra işe başlayan çalışanların isim ve soyisim bilgilerini getiriniz.**

```sql
SELECT 
    employee_id,
    first_name,
    last_name,
    department_id,
    hire_date,
    LAG(first_name || ' ' || last_name, 1) OVER (PARTITION BY department_id ORDER BY hire_date) AS previous_employee,
    LAG(hire_date, 1) OVER (PARTITION BY department_id ORDER BY hire_date) AS previous_hire_date,
    LEAD(first_name || ' ' || last_name, 1) OVER (PARTITION BY department_id ORDER BY hire_date) AS next_employee,
    LEAD(hire_date, 1) OVER (PARTITION BY department_id ORDER BY hire_date) AS next_hire_date
FROM employees
WHERE department_id IS NOT NULL
ORDER BY department_id, hire_date;
```

---
## Fonksiyonlar Özeti

### 📌 Pencere Fonksiyonları (Window Functions)
- **`LEAD`**: Sonraki satırdaki değeri getirir
- **`LAG`**: Önceki satırdaki değeri getirir
- **`ROW_NUMBER`**: Sıralı benzersiz numara verir
- **`RANK`**: Sıralama yapar (aynı değerler için boşluk bırakır)
- **`DENSE_RANK`**: Sıralama yapar (aynı değerler için boşluk bırakmaz)
- **`NTILE`**: Veriyi eşit gruplara böler

### 📊 Agregat Fonksiyonları (Pencere Olarak)
- **`MAX/MIN/SUM/AVG`**: Pencere fonksiyonu olarak kullanılır
- **`LISTAGG`**: Değerleri birleştirip tek sütunda gösterir

### 🔧 Yardımcı Yapılar
- **`PARTITION BY`**: Veriyi gruplara ayırır
- **`OVER`**: Pencere fonksiyonu tanımlar
- **`CASE WHEN`**: Koşullu mantık
- **`NVL`**: NULL değer kontrolü
---

