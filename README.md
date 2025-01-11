# Raspberry Pi Brown Noise Player

This project allows you to play brown noise from a Java server application and control it via a simple website hosted on your Raspberry Pi. The setup includes transferring files, configuring a Java backend, and setting up an NGINX server to serve the control interface. Follow the steps below to set up and run this project on your Raspberry Pi.

## Prerequisites

1. A Raspberry Pi with Wi-Fi capability or Ethernet.
2. A computer with an SD card reader.
3. An SD card (16GB or larger recommended).
4. The following software installed on your computer:
   - Raspberry Pi Imager ([Download here](https://www.raspberrypi.com/software/))
   - SSH client (e.g., built-in `ssh` on macOS/Linux or PuTTY on Windows).

## Step 1: Install Ubuntu Server on the Raspberry Pi

1. Insert the SD card into your computer.
2. Open Raspberry Pi Imager.
3. Select **Ubuntu Server** as the operating system.
4. Choose your SD card as the storage location.
5. Click "Write" to flash the operating system to the SD card.
6. Once done, insert the SD card into the Raspberry Pi and power it on.

## Step 2: Connect to Your Raspberry Pi

1. Find the IP address of your Raspberry Pi using your router’s admin panel or a network scanning tool.
2. Open a terminal on your computer and SSH into the Raspberry Pi:
   ```bash
   ssh <your-username>@<raspberry-pi-ip>
   ```
   Replace `<raspberry-pi-ip>` with the IP address of your Raspberry Pi and `<your-username>` with the username of your Raspberry Pi user (default is `pi`).

## Step 3: Transfer Project Files to the Raspberry Pi

1. Place your `.html` and `.jar` files in a directory on your computer.
2. Use `scp` to copy the files to your Raspberry Pi:
   ```bash
   scp Noise.html my-backend-1.0.jar <your-username>@<raspberry-pi-ip>:/home/<your-username>/
   ```

## Step 4: Configure NGINX to Serve the HTML File

1. Install NGINX on the Raspberry Pi:
   ```bash
   sudo apt update
   sudo apt install nginx -y
   ```
2. Move the `.html` file to NGINX's web directory:
   ```bash
   sudo mv /home/<your-username>/Noise.html /var/www/html/index.html
   ```
3. Restart NGINX:
   ```bash
   sudo systemctl restart nginx
   ```
4. Test your setup by visiting your Raspberry Pi’s IP address in a browser:
   ```
   http://<raspberry-pi-ip>/
   ```

## Step 5: Configure the JAR File to Run on Startup

1. Create a systemd service file for the Java application:
   ```bash
   sudo nano /etc/systemd/system/my-backend.service
   ```
2. Add the following content:
   ```ini
   [Unit]
   Description=My Backend Service
   After=network.target

   [Service]
   ExecStart=/usr/bin/java -Xms512m -Xmx1024m -jar /home/<your-username>/my-backend-1.0.jar
   WorkingDirectory=/home/<your-username>
   Restart=always
   User=<your-username>
   Group=<your-username>

   [Install]
   WantedBy=multi-user.target
   ```
3. Save and exit the file.
4. Reload the systemd daemon to apply the changes:
   ```bash
   sudo systemctl daemon-reload
   ```
5. Enable the service to run on startup:
   ```bash
   sudo systemctl enable my-backend.service
   ```
6. Start the service:
   ```bash
   sudo systemctl start my-backend.service
   ```

## Step 6: Verify Startup Configuration

1. Reboot your Raspberry Pi:
   ```bash
   sudo reboot
   ```
2. After rebooting:
   - Check the NGINX server by visiting `http://<raspberry-pi-ip>/` in your browser.
   - Verify the Java application is running:
     ```bash
     sudo systemctl status my-backend.service
     ```

## Additional Notes

- Ensure that your `.html` file and `.jar` file are updated as needed before transferring them to the Raspberry Pi.
- Modify the `java` command in the systemd service file if additional arguments are required for your application.

Feel free to raise an issue in this repository if you encounter any problems or have questions!

