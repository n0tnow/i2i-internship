# Basic Ping Test with Cloud Service (CLOUD-EX-01-i2i)

This project demonstrates how to create a remote virtual machine (instance) using a cloud service provider and test the network connection by sending a `ping` command from the local machine to the cloud instance.

## ☁️ Cloud Provider Used

- **Amazon Web Services (AWS) EC2**

## 🔧 Steps Performed

1. A free-tier AWS account was created.
2. An EC2 virtual machine (instance) was launched using the AWS Console.
3. The following configurations were applied:
   - Operating System: Ubuntu Server 22.04 LTS
   - Instance Type: t3.micro (free tier eligible)
   - Security Group Rules:
     - SSH (port 22) → for remote access
     - ICMP (All IPv4) → for allowing ping tests
4. The instance’s `Public IPv4` address was retrieved: `16.171.17.42`
5. A `ping` test was executed from the local machine to verify the network connection.

## ✅ Ping Test Result

```bash
Pinging 16.171.17.42 with 32 bytes of data:
Reply from 16.171.17.42: bytes=32 time=91ms TTL=49
Reply from 16.171.17.42: bytes=32 time=105ms TTL=49
Reply from 16.171.17.42: bytes=32 time=88ms TTL=49
Reply from 16.171.17.42: bytes=32 time=94ms TTL=49

Ping statistics for 16.171.17.42:
    Packets: Sent = 4, Received = 4, Lost = 0 (0% loss),
Approximate round trip times in milli-seconds:
    Minimum = 88ms, Maximum = 105ms, Average = 94ms
