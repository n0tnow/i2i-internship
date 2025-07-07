# Oracle XE Docker Setup

## 🎯 Project Objective
Run Oracle Database 21c Express Edition as a Docker container and connect via SQL*Plus CLI.

## 📋 Requirements
- Docker Desktop
- Git
- 15GB free disk space

## 🚀 Installation Steps

### 1. Clone Repository
```bash
git clone https://github.com/oracle/docker-images.git
cd docker-images/OracleDatabase/SingleInstance/dockerfiles/21.3.0
```

### 2. Download Oracle XE
- Visit: https://www.oracle.com/database/technologies/xe-downloads.html
- Download **"Oracle Database 21c Express Edition for Linux x64 (OL7)"**
- Place `oracle-database-xe-21c-1.0-1.ol7.x86_64.rpm` file in 21.3.0 folder

### 3. Build Docker Image
```bash
cd ..
./buildContainerImage.sh -v 21.3.0 -x
```

### 4. Run Container
```bash
docker run --name oraclexe \
  -p 1521:1521 \
  -p 5500:5500 \
  -e ORACLE_PWD=ORACLE \
  -d \
  oracle/database:21.3.0-xe

# Wait for database to be ready
docker logs -f oraclexe
# Wait for "DATABASE IS READY TO USE!" message
```

## 🔗 Database Connection

```bash
# Access container
docker exec -it oraclexe bash

# Connect to SQL*Plus
sqlplus sys/ORACLE@//localhost:1521/XE as sysdba
```

## 📊 Test Commands

```sql
-- Check database name
select name from v$database;

-- Create test table
create table test_table (id number, name varchar2(50));
insert into test_table values (1, 'Hello Oracle XE!');
commit;
select * from test_table;

-- Exit
exit
```

## 🐳 Container Management

```bash
# Stop/Start
docker stop oraclexe
docker start oraclexe

# Remove
docker rm oraclexe
docker rmi oracle/database:21.3.0-xe
```

## 📸 Screenshot Checklist (Assignment)
1. Docker version check
2. Repository cloning
3. Oracle XE file download (2.3GB)
4. Docker image build completion
5. Container running (`docker ps`)
6. "DATABASE IS READY TO USE!" message
7. SQL*Plus connection
8. `select name from v$database;` result
9. Test table creation and data insertion

---
