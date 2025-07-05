# VoltDB Docker Project

Simple VoltDB Community Edition setup using Docker for telecommunications data processing.

## Quick Start

### 1. Setup Docker Network
```bash
docker network create voltLocalCluster
```

### 2. Run VoltDB Container
```bash
docker pull full360/docker-voltdb-ce
docker run -d -p 8082:8080 -p 21213:21212 -e BUSY_COUNT=1 -e HOSTS=node1 --name=node1-clean --network=voltLocalCluster full360/docker-voltdb-ce:latest
```

### 3. Verify Installation
```bash
docker logs node1-clean
docker exec -it node1-clean sqlcmd
```

## Access Points

| Service | URL | Description |
|---------|-----|-------------|
| Web UI | `http://localhost:8082` | Management Console |
| SQL Client | `localhost:21213` | Database Connection |

## Sample Usage

### Connect via Terminal
```bash
docker exec -it node1-clean sqlcmd
```

### Create Sample Table (Exercise mth3902)
```sql
CREATE TABLE mth3902 (
    id BIGINT NOT NULL,
    start_date_epoch BIGINT,
    create_user VARCHAR(32),
    CONSTRAINT mth3902_pk PRIMARY KEY(id)
);

PARTITION TABLE mth3902 ON COLUMN id;

INSERT INTO mth3902 (id, start_date_epoch, create_user) VALUES (1, 1698295044, 'MENNAN');
INSERT INTO mth3902 (id, start_date_epoch, create_user) VALUES (2, 1698295088, 'ERKUT');

SELECT * FROM mth3902 LIMIT 1;
```

### View Data via Web UI
1. Open `http://localhost:8082`
2. Go to **SQL Query** tab
3. Run: `SELECT * FROM mth3902;`

## DBeaver Connection

**JDBC URL**: `jdbc:voltdb://localhost:21213`  
**Username/Password**: Leave empty

**Note**: If DBeaver meta data loading fails, use terminal instead.

## Container Management

```bash
# Stop container
docker stop node1-clean

# Start container
docker start node1-clean

# Remove container
docker rm node1-clean
```

## Technology Stack

- **VoltDB 6.4 Community Edition**
- **Docker Container**
- **Full360 Docker Image**

---

**Use Case**: Real-time telecommunications data processing and analytics.
