# Tire Retreading Management Software

## 📌 Project Overview
Tire Retreading Management Software is a desktop application designed to help users efficiently manage physical assets (e.g., tires) using **RFID technology**.  
The system enables tracking, management, and reporting of items in a way that is both intuitive and powerful.  
The main goal of the project is to provide a complete management solution that improves asset traceability, reduces errors, and supports real-world operations such as inventory tracking and tire lifecycle management.

---

## 📌 Key Features
- **RFID-based object tracking** using an external reader  
- **Persistent data storage and retrieval**  
- Intuitive **JavaFX graphical user interface**  
- **Barcode / RFID tag assignment and management**  
- Reports with export support  
- Seamless integration with database using Hibernate ORM

---

## 🛠 Technologies Used
The project is built using modern Java-based technologies:

| Technology | Usage |
|------------|-------|
| **Java 17 LTS** | Application logic |
| **JavaFX 19 LTS** | User interface |
| **Maven 3.8.1** | Dependency and project management |
| **Hibernate 5.6.13 Final** | ORM (Object Relational Mapping) |
| **jSerialComm 2.9.3** | Serial communication with RFID reader |
| **Java Persistence 2.2** | Persistence API |
| **iText 7.2.4** | PDF generation and reporting |

---

## 📂 Project Structure
```bash
├── .idea/
├── src/
│ ├── main/
│ │ ├── java/
│ │ ├── resources/
│
├── RFID Reader.ino # Sketch for Arduino RFID reader
├── pom.xml # Maven configuration
└── README.md
```
---

## ⚙️ Requirements
To run this project locally on your machine, you need:

✔ **Java 17 JDK**  
✔ **Maven**  
✔ **RFID Reader hardware (optional)**  
✔ **Database** (configured via Hibernate — MySQL)

---

## ▶️ How to Compile and Run

### 1️⃣ Clone the repository
```bash
git clone https://github.com/Achaj/Tire_retreading_management_software.git
cd Tire_retreading_management_software
```
### 2️⃣ Run MySQL Server on port 3306
  Copy the contents of the insert.txt file into the server console and run the script.
  
### 3️⃣ Build with Maven
```
mvn clean install
```
### 4️⃣ Run the application
```
mvn javafx:run
```
⚠ Ensure your database is running and configured in the hibernate.cfg.xml file before starting the application.

## 📌 Hardware Integration (RFID)
The repository includes:
- RFID Reader.ino — Arduino sketch for RFID reader communication
- This script enables the software to interface with RFID tags through a USB/serial-connected reader.
- To use the hardware features:
- Upload the .ino code to your Arduino-compatible reader
- Connect the reader to your computer
- Ensure the COM port is correctly configured in the software

## 🧪 Example Use Cases
- This software can be applied to real-world scenarios such as:
- Tire retreading inventory tracking
- Warehouse object tracking and reporting
- Asset management with RFID tags
- Production line logistics monitoring

## 📝 License

This project is intended for educational and research purposes.
