# DevOps Exercise Solutions - DEVOPS-EX-01

🚀 **[Live Demo](https://n0tnow.github.io/git-commands-guide/)**
---

# Question 1: Configuration as Code (CaC) and Infrastructure as Code (IaC)

## Infrastructure as Code (IaC)

Infrastructure as Code is the practice of managing and provisioning computing infrastructure through machine-readable definition files, rather than through manual processes or interactive configuration tools.

### Key Benefits:
- **Version Control:** Infrastructure changes are tracked
- **Reproducibility:** Same infrastructure can be recreated anywhere
- **Consistency:** Eliminates configuration drift
- **Automation:** Reduces manual errors

### Example - Terraform:
```hcl
resource "aws_instance" "web_server" {
  ami           = "ami-0c55b159cbfafe1d0"
  instance_type = "t2.micro"
  
  tags = {
    Name = "WebServer"
    Environment = "Production"
  }
}

resource "aws_security_group" "web_sg" {
  name = "web-security-group"
  
  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
```

## Configuration as Code (CaC)

Configuration as Code involves managing application and system configurations through version-controlled code files rather than manual configuration.

### Example - Ansible Playbook:
```yaml
---
- name: Configure web servers
  hosts: webservers
  become: yes
  tasks:
    - name: Install nginx
      apt:
        name: nginx
        state: present
    
    - name: Start nginx service
      systemd:
        name: nginx
        state: started
        enabled: yes
    
    - name: Copy configuration file
      template:
        src: nginx.conf.j2
        dest: /etc/nginx/nginx.conf
      notify: restart nginx
```

---

# Question 2: Differences Between Terraform, Ansible, and CloudFormation

## Comparison Table

| Aspect | Terraform | Ansible | CloudFormation |
|--------|-----------|---------|----------------|
| **Primary Purpose** | Infrastructure Provisioning | Configuration Management | AWS Infrastructure Provisioning |
| **Cloud Support** | Multi-cloud (AWS, Azure, GCP, etc.) | Multi-cloud + On-premises | AWS Only |
| **State Management** | Tracks state in files | Stateless (idempotent) | AWS manages state |
| **Language** | HCL (HashiCorp Configuration Language) | YAML Playbooks | JSON/YAML Templates |
| **Agent Required** | No | No | No |
| **Execution** | Declarative | Procedural/Declarative | Declarative |

## Detailed Analysis

### Terraform
- **Best for:** Multi-cloud infrastructure provisioning
- **Strengths:** Excellent state management, planning capabilities, strong community
- **Use Case:** Complex infrastructure across multiple cloud providers

### Ansible  
- **Best for:** Configuration management and application deployment
- **Strengths:** Agentless, simple YAML syntax, idempotent operations
- **Use Case:** Server configuration and application deployment automation

### CloudFormation
- **Best for:** AWS-only environments with native AWS integration
- **Strengths:** Deep AWS integration, no state files to manage, AWS support
- **Use Case:** Pure AWS infrastructure with AWS-native tooling

---

# Question 3: Docker Compose and Main Use Cases

## What is Docker Compose?

Docker Compose is a tool for defining and running multi-container Docker applications. It uses a YAML file to configure application services, networks, and volumes.

## Main Use Cases

### 1. Development Environment Setup
- Quickly spin up complex applications with multiple services
- Ensure consistent development environments across team members

### 2. Microservices Architecture
- Orchestrate multiple interconnected services
- Define service dependencies and communication

### 3. Testing Environments
- Create isolated testing environments
- Integration testing with multiple services

### 4. Local Production Simulation
- Replicate production-like environments locally
- Test service interactions

## Example Use Case - Web Application Stack:
```yaml
version: '3.8'
services:
  web:
    image: nginx:latest
    ports:
      - "80:80"
    depends_on:
      - api
  
  api:
    image: node:16
    environment:
      - DB_HOST=database
    depends_on:
      - database
  
  database:
    image: postgres:13
    environment:
      - POSTGRES_DB=myapp
      - POSTGRES_PASSWORD=secret
```

---

# Question 4: Default Network Type in Docker Compose

## Default Network Type

Docker Compose creates a **bridge network** by default when you run `docker-compose up`.

## Service Communication

### Network Characteristics:
- **Network Name:** `{project-name}_default`
- **Service Discovery:** Services can communicate using service names as hostnames
- **Internal DNS:** Docker provides built-in DNS resolution
- **Port Access:** Services can access each other's internal ports without publishing

### Communication Example:
```yaml
version: '3.8'
services:
  frontend:
    image: nginx:latest
    # Can access backend at http://backend:3000
  
  backend:
    image: node:16
    # Can access database at postgresql://database:5432
  
  database:
    image: postgres:13
```

### How Services Communicate:
- Frontend can reach backend at `http://backend:3000`
- Backend can reach database at `database:5432`
- No external port exposure needed for internal communication
- Docker's internal DNS resolves service names to container IPs

---

# Question 5: Complete Docker Compose Configuration

## Complete docker-compose.yml Solution:

```yaml
version: '3.8'
services:
  backend:
    image: python:3.9
    # Add necessary configuration for the backend service
    container_name: backend_service
    ports:
      - "5000:5000"
    volumes:
      - ./backend:/app
    working_dir: /app
    command: python app.py
    environment:
      - FLASK_ENV=development
      - FLASK_APP=app.py
    networks:
      - app_network
    
  frontend:
    image: nginx:latest
    # Add necessary configuration for the frontend service
    container_name: frontend_service
    ports:
      - "80:80"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf
      - ./frontend:/usr/share/nginx/html
    depends_on:
      - backend
    networks:
      - app_network
    # Ensure it can communicate with the backend on port 5000
    # Frontend can reach backend at http://backend:5000

networks:
  app_network:
    driver: bridge

volumes:
  backend_data:
```

## Key Configuration Points:

### Backend Service:
- Exposes port 5000 internally and externally
- Mounts application code from local directory
- Sets working directory for Python application
- Configures Flask environment variables

### Frontend Service:
- Uses nginx to serve static content
- Depends on backend service (starts after backend)
- Can communicate with backend using service name

### Network Communication:
- Both services are on the same custom network
- Frontend can access backend using `http://backend:5000`
- Internal communication doesn't require external port mapping

---

# Question 6: Continuous Integration (CI) vs Continuous Deployment (CD)

## Continuous Integration (CI)

### Definition:
The practice of regularly integrating code changes into a shared repository, with automated testing to detect integration issues early.

### Key Characteristics:
- **Frequent commits** to main branch
- **Automated testing** on each commit
- **Build verification** ensures code compiles
- **Fast feedback** to developers

### Benefits:
- Early detection of integration issues
- Reduced integration complexity
- Improved code quality
- Faster development cycles

## Continuous Deployment (CD)

### Definition:
The practice of automatically deploying every code change that passes the automated testing pipeline to production.

### Key Characteristics:
- **Automated deployment** to production
- **No manual intervention** required
- **High confidence** in automated testing
- **Immediate release** of features

### Benefits:
- Faster time to market
- Reduced deployment risk
- Consistent deployment process
- Immediate user feedback

## Comparison Summary

| Aspect | CI | CD |
|--------|----|----|
| **Focus** | Integration and Testing | Deployment to Production |
| **Frequency** | Every commit | Every successful build |
| **Manual Steps** | Minimal (code review) | None (fully automated) |
| **Risk Level** | Low (early detection) | Requires robust testing |
| **Feedback Speed** | Minutes to hours | Immediate user feedback |

---

# Question 7: CI/CD Pipeline Components

## What is a Pipeline?

A pipeline is an automated sequence of processes that code goes through from development to production deployment.

## Essential Pipeline Steps

### 1. Code Quality Assurance
```yaml
quality_gates:
  - linting: "ESLint, Pylint, SonarQube"
  - code_format: "Prettier, Black, gofmt"
  - complexity_analysis: "Cyclomatic complexity checks"
  - dependency_scanning: "Check for vulnerable dependencies"
```

### 2. Testing Stages
```yaml
testing_pyramid:
  unit_tests:
    - coverage: "Minimum 80%"
    - tools: "Jest, PyTest, JUnit"
  
  integration_tests:
    - api_testing: "Postman, Newman"
    - database_testing: "Test DB connections"
  
  e2e_tests:
    - browser_testing: "Selenium, Cypress"
    - user_scenarios: "Critical user journeys"
```

### 3. Security Scanning
```yaml
security_checks:
  - sast: "Static Application Security Testing"
  - dast: "Dynamic Application Security Testing"
  - container_scanning: "Docker image vulnerability scan"
  - secrets_detection: "No hardcoded passwords/keys"
```

### 4. Build and Package
```yaml
build_process:
  - compile: "Build application artifacts"
  - containerize: "Create Docker images"
  - tag: "Version with git commit hash"
  - registry_push: "Store in artifact repository"
```

### 5. Deployment Strategy
```yaml
deployment:
  staging:
    - deploy_to_staging: "Automated deployment"
    - smoke_tests: "Basic functionality verification"
    - performance_tests: "Load testing"
  
  production:
    - blue_green: "Zero-downtime deployment"
    - canary: "Gradual rollout to subset of users"
    - rollback_plan: "Automated rollback on failure"
```

## Pipeline Benefits

### Code Quality Assurance:
- Consistent code standards
- Early bug detection
- Security vulnerability prevention

### Safe Deployment:
- Automated testing reduces human error
- Rollback capabilities for quick recovery
- Gradual deployment strategies minimize risk

---

# Question 8: Branch-Based Pipeline Configuration

## Configuration Strategy

### Objective:
- **Deploy only:** When code is merged to main branch
- **Run tests:** On every pull request

## GitHub Actions Implementation

```yaml
name: CI/CD Pipeline

on:
  push:
    branches: [ main ]  # Deploy only on main branch
  pull_request:
    branches: [ main ]  # Run tests on every PR

jobs:
  test:
    runs-on: ubuntu-latest
    # This job runs on EVERY push and PR
    steps:
      - uses: actions/checkout@v3
      
      - name: Setup Node.js
        uses: actions/setup-node@v3
        with:
          node-version: '16'
      
      - name: Install dependencies
        run: npm install
      
      - name: Run tests
        run: npm test
      
      - name: Run linting
        run: npm run lint
      
      - name: Security audit
        run: npm audit

  deploy:
    runs-on: ubuntu-latest
    needs: test
    # This job ONLY runs on main branch
    if: github.ref == 'refs/heads/main' && github.event_name == 'push'
    
    steps:
      - uses: actions/checkout@v3
      
      - name: Build application
        run: npm run build
      
      - name: Deploy to staging
        run: |
          echo "Deploying to staging environment"
          # Deployment commands here
      
      - name: Run smoke tests
        run: |
          echo "Running post-deployment tests"
          # Smoke test commands
      
      - name: Deploy to production
        if: success()
        run: |
          echo "Deploying to production"
          # Production deployment commands
```

## Key Configuration Elements

### Trigger Conditions:
- **`on.push.branches: [main]`** - Triggers on push to main
- **`on.pull_request.branches: [main]`** - Triggers on PR to main

### Conditional Execution:
- **`if: github.ref == 'refs/heads/main'`** - Only deploy on main branch
- **`needs: test`** - Deploy job waits for test completion

### Workflow Behavior:
- **Pull Request:** Only runs tests, no deployment
- **Main Branch Push:** Runs tests then deploys if tests pass

---

# Question 9: Prometheus-Grafana Troubleshooting

## Problem: Grafana Cannot Display Prometheus Metrics

### Possible Issues and Solutions

#### 1. Network Connectivity Problems

**Symptoms:** Grafana cannot reach Prometheus

**Solution - Docker Compose Network:**
```yaml
version: '3.8'
services:
  prometheus:
    image: prom/prometheus
    container_name: prometheus
    ports:
      - "9090:9090"
    networks:
      - monitoring

  grafana:
    image: grafana/grafana
    container_name: grafana
    ports:
      - "3000:3000"
    networks:
      - monitoring

networks:
  monitoring:
    driver: bridge
```

#### 2. Incorrect Grafana Data Source Configuration

**Problem:** Wrong Prometheus URL in Grafana

**Solution - Data Source Config:**
```json
{
  "name": "Prometheus",
  "type": "prometheus",
  "url": "http://prometheus:9090",
  "access": "proxy",
  "isDefault": true
}
```

**Key Point:** Use service name `prometheus`, not `localhost`

#### 3. Prometheus Configuration Issues

**Problem:** Prometheus not scraping metrics

**Solution - prometheus.yml:**
```yaml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'prometheus'
    static_configs:
      - targets: ['localhost:9090']
  
  - job_name: 'your-application'
    static_configs:
      - targets: ['app:8080']
    metrics_path: '/metrics'
    scrape_interval: 5s
```

#### 4. Application Not Exposing Metrics

**Problem:** Application doesn't have /metrics endpoint

**Solution - Python Flask Example:**
```python
from prometheus_client import Counter, generate_latest, REGISTRY
from flask import Flask, Response

app = Flask(__name__)
request_count = Counter('app_requests_total', 'Total app requests')

@app.route('/metrics')
def metrics():
    return Response(generate_latest(REGISTRY), mimetype='text/plain')

@app.route('/')
def hello():
    request_count.inc()
    return "Hello World!"
```

#### 5. Complete Working Configuration

**Docker Compose Fix:**
```yaml
version: '3.8'

services:
  app:
    image: your-app:latest
    container_name: your_app
    ports:
      - "8080:8080"
    networks:
      - monitoring

  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    ports:
      - "9090:9090"
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
      - prometheus_data:/prometheus
    command:
      - '--config.file=/etc/prometheus/prometheus.yml'
      - '--storage.tsdb.path=/prometheus'
    networks:
      - monitoring

  grafana:
    image: grafana/grafana:latest
    container_name: grafana
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin123
    volumes:
      - grafana_data:/var/lib/grafana
    networks:
      - monitoring
    depends_on:
      - prometheus

networks:
  monitoring:
    driver: bridge

volumes:
  prometheus_data:
  grafana_data:
```

## Troubleshooting Checklist

### Network Issues:
- ✅ Services on same Docker network
- ✅ Correct service names used in URLs
- ✅ Ports properly exposed

### Configuration Issues:
- ✅ Prometheus scraping correct targets
- ✅ Grafana data source pointing to right URL
- ✅ Application exposing /metrics endpoint

### Data Issues:
- ✅ Metrics actually being generated
- ✅ Prometheus successfully scraping
- ✅ Grafana queries syntactically correct

---

# Question 10: Common DevOps Tools

## 1. Git - Version Control System

**Purpose:** Source code management and collaboration

**Key Features:**
- Distributed version control
- Branch management and merging
- Collaboration through pull requests
- History tracking and rollback capabilities

**Use Case:** Managing code changes in team development

## 2. Docker - Containerization Platform

**Purpose:** Application packaging and deployment

**Key Features:**
- Container creation and management
- Portable application environments
- Resource isolation and efficiency
- Microservices architecture support

**Use Case:** Consistent application deployment across environments

## 3. Kubernetes - Container Orchestration

**Purpose:** Automated deployment, scaling, and management of containerized applications

**Key Features:**
- Service discovery and load balancing
- Auto-scaling and self-healing
- Rolling updates and rollbacks
- Storage orchestration

**Use Case:** Managing containerized applications at scale

## 4. Terraform - Infrastructure as Code

**Purpose:** Infrastructure provisioning and management

**Key Features:**
- Multi-cloud support
- Declarative configuration
- State management
- Resource dependency handling

**Use Case:** Automating infrastructure deployment

## 5. Ansible - Configuration Management

**Purpose:** Application deployment and configuration automation

**Key Features:**
- Agentless architecture
- Playbook-based automation
- Idempotent operations
- Multi-platform support

**Use Case:** Server configuration and application deployment

## 6. Jenkins - CI/CD Automation Server

**Purpose:** Build automation and continuous integration

**Key Features:**
- Pipeline as code
- Extensive plugin ecosystem
- Distributed builds
- Integration with multiple tools

**Use Case:** Automating build, test, and deployment processes

---

# Question 11: Branching Strategies

## Preferred Strategy: Git Flow

### Why Git Flow?

#### Advantages:
1. **Clear Structure:** Well-defined branch purposes
2. **Release Management:** Dedicated release preparation
3. **Hotfix Support:** Emergency fixes without disrupting development
4. **Parallel Development:** Multiple features can be developed simultaneously
5. **Stable Main Branch:** Main branch always contains production-ready code

### Branch Structure:
```
main (production)
├── develop (integration)
│   ├── feature/user-authentication
│   ├── feature/payment-system
│   └── feature/dashboard-ui
├── release/v1.2.0
└── hotfix/critical-security-fix
```

### Workflow Process:
1. **Feature Development:** Create feature branches from develop
2. **Integration:** Merge completed features into develop
3. **Release Preparation:** Create release branch from develop
4. **Production Deployment:** Merge release into main
5. **Emergency Fixes:** Create hotfix branches from main

### Implementation Example:
```bash
# Start new feature
git checkout develop
git checkout -b feature/user-authentication

# Complete feature
git checkout develop
git merge feature/user-authentication

# Prepare release
git checkout -b release/v1.2.0
# Bug fixes and testing

# Deploy to production
git checkout main
git merge release/v1.2.0
git tag v1.2.0
```

### Alternative for Smaller Teams: GitHub Flow

**Simpler Approach:**
- Only main and feature branches
- Continuous deployment friendly  
- Less overhead for small projects

**When to Use GitHub Flow:**
- Small teams (2-5 developers)
- Rapid deployment cycles
- Simple project structure

## Conclusion

**Git Flow** is preferred for larger teams and complex projects due to its structured approach to release management and parallel development support.

---

# Question 12: Essential Git Commands

## 1. git init
**Purpose:** Initialize a new Git repository
```bash
git init
git init --bare  # Create bare repository
```
**Use Case:** Starting version control for a new project

## 2. git clone
**Purpose:** Create a local copy of a remote repository
```bash
git clone https://github.com/user/repo.git
git clone --depth 1 <url>  # Shallow clone
```
**Use Case:** Getting started with existing projects

## 3. git add
**Purpose:** Stage changes for commit
```bash
git add file.txt          # Stage single file
git add .                # Stage all changes
git add -p               # Interactive staging
```
**Use Case:** Preparing changes for commit

## 4. git commit
**Purpose:** Save staged changes to repository
```bash
git commit -m "Add user authentication"
git commit --amend       # Modify last commit
git commit -a -m "msg"   # Stage and commit
```
**Use Case:** Creating snapshots of project state

## 5. git status
**Purpose:** Show working directory and staging area status
```bash
git status
git status --short       # Compact output
git status --porcelain   # Script-friendly format
```
**Use Case:** Understanding current repository state

## 6. git log
**Purpose:** Display commit history
```bash
git log --oneline        # Compact format
git log --graph          # Visual branch structure
git log --author="John"  # Filter by author
```
**Use Case:** Reviewing project history and changes

## 7. git branch
**Purpose:** List, create, or delete branches
```bash
git branch                    # List branches
git branch feature/login     # Create branch
git branch -d feature/login  # Delete branch
```
**Use Case:** Managing parallel development streams

## 8. git checkout
**Purpose:** Switch branches or restore files
```bash
git checkout main            # Switch to branch
git checkout -b new-feature  # Create and switch
git checkout -- file.txt    # Discard changes
```
**Use Case:** Navigating between different code versions

## 9. git merge
**Purpose:** Combine changes from different branches
```bash
git merge feature/login     # Merge branch
git merge --no-ff branch   # Force merge commit
git merge --abort          # Cancel merge
```
**Use Case:** Integrating completed features

## 10. git push/pull
**Purpose:** Synchronize with remote repositories
```bash
# Push changes
git push origin main
git push -u origin feature  # Set upstream

# Pull changes  
git pull origin main
git pull --rebase origin main
```
**Use Case:** Collaborating with team members

## Git Commands Summary

These 10 commands form the foundation of Git workflow:
- **Repository Management:** init, clone
- **Change Tracking:** add, commit, status
- **History Review:** log
- **Branch Management:** branch, checkout, merge
- **Remote Collaboration:** push, pull

Mastering these commands and understanding their underlying concepts is essential for effective version control and team collaboration.
