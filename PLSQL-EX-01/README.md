sql
```
CREATE OR REPLACE PROCEDURE print_string_formatted(p_input_string VARCHAR2) IS
    TYPE char_array IS TABLE OF CHAR(1) INDEX BY PLS_INTEGER;
    stack char_array;
    
    string_length NUMBER;
    current_char CHAR(1);
    previous_char CHAR(1);
    indent_level NUMBER := 0;
    indent_str VARCHAR2(50) := '';
    stack_size NUMBER := 0;
    found_index NUMBER := -1;
    
BEGIN
    -- SET SERVEROUTPUT ON yerine bunu kullan
    DBMS_OUTPUT.ENABLE(1000000);
    
    string_length := LENGTH(p_input_string);
    
    IF string_length > 30 THEN
        DBMS_OUTPUT.PUT_LINE('HATA: String uzunluğu 30 karakterden fazla olamaz!');
        RETURN;
    END IF;
    
    DBMS_OUTPUT.PUT_LINE('String: ' || p_input_string);
    
    -- İlk karakteri işle
    current_char := SUBSTR(p_input_string, 1, 1);
    stack_size := stack_size + 1;
    stack(stack_size) := current_char;
    DBMS_OUTPUT.PUT_LINE(current_char);
    previous_char := current_char;
    indent_level := 0;
    
    -- Geriye kalan karakterleri işle
    FOR i IN 2..string_length LOOP
        current_char := SUBSTR(p_input_string, i, 1);
        
        -- Karakter analizi
        IF current_char = previous_char THEN
            -- Aynı karakter: aynı sütunda yazdır
            indent_str := '';
            FOR j IN 1..indent_level LOOP
                indent_str := indent_str || ' ';
            END LOOP;
            DBMS_OUTPUT.PUT_LINE(indent_str || current_char);
            
        ELSE
            -- Farklı karakter: stack'te bu karakterin olup olmadığını kontrol et
            found_index := -1;
            
            -- Stack'te geriye doğru ara (en son pozisyonu bul)
            FOR j IN REVERSE 1..stack_size LOOP
                IF stack(j) = current_char THEN
                    found_index := j;
                    EXIT; -- En son pozisyonu bulduk
                END IF;
            END LOOP;
            
            IF found_index > 0 THEN
                -- Geri dönüş: stack'i found_index seviyesine kadar temizle
                FOR j IN REVERSE (found_index + 1)..stack_size LOOP
                    stack.DELETE(j);
                END LOOP;
                stack_size := found_index;
                indent_level := found_index - 1;
                
            ELSE
                -- Yeni karakter: stack'e ekle
                indent_level := indent_level + 1;
                stack_size := stack_size + 1;
                stack(stack_size) := current_char;
            END IF;
            
            -- Indent string'i oluştur ve yazdır
            indent_str := '';
            FOR j IN 1..indent_level LOOP
                indent_str := indent_str || ' ';
            END LOOP;
            
            DBMS_OUTPUT.PUT_LINE(indent_str || current_char);
        END IF;
        
        previous_char := current_char;
    END LOOP;
    
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('HATA: ' || SQLCODE || ' - ' || SQLERRM);
END;
```
Örnek 1
```
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Test 1 ===');
    print_string_formatted('acbbcadefghkkhgfed');
END;
```
Örnek 2
```
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Test 2 (Optimized) ===');
    print_string_formatted('abbcddfggfca');
END;

```

Çıktılar

<img width="460" height="341" alt="image" src="https://github.com/user-attachments/assets/4ede2e23-1e43-42ec-b224-3fcb95fe7b09" />

<img width="460" height="341" alt="image" src="https://github.com/user-attachments/assets/af2f2558-fc78-4913-877d-8bf13e5e7500" />
