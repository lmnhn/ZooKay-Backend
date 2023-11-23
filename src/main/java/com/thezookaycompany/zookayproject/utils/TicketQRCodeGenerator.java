package com.thezookaycompany.zookayproject.utils;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.thezookaycompany.zookayproject.model.entity.Orders;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TicketQRCodeGenerator {
    private static final String connectionString = "DefaultEndpointsProtocol=https;AccountName=cs110032002f9d9b454;AccountKey=oNT2cJxNjZ9QBPeAYe0sRYKOV54vN8zGaxpy8LemIM96nWfCdBzxahLB3V2l8AgE+loUEI2sr5Dk+AStzvPigg==;EndpointSuffix=core.windows.net";
    private static final String containerName = "qrcode";

    public void generateAndUploadQRCodeImage(Orders orders, int width, int height) throws WriterException, IOException {
        // Generate QR code
        byte[] qrCodeData = generateQRCodeImage(orders, width, height);

        uploadQRCodeToBlob(orders.getOrderID() + "-QRCODE.png", qrCodeData);
    }

    private byte[] generateQRCodeImage(Orders orders, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
                orders.getEmail() + orders.getOrderID(),
                BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        return pngOutputStream.toByteArray();
    }

    private void uploadQRCodeToBlob(String blobName, byte[] qrCodeData) {
        try {
            var blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();

            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);

            // Check if the blob already exists
            if (blobContainerClient.getBlobClient(blobName).exists()) {
                // Handle the case where the blob already exists
                System.out.println("Blob with name " + blobName + " already exists.");
                return;
            }
            BlockBlobClient blockBlobClient = blobContainerClient.getBlobClient(blobName).getBlockBlobClient();

            try (ByteArrayInputStream dataStream = new ByteArrayInputStream(qrCodeData)) {
                blockBlobClient.upload(dataStream, qrCodeData.length);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
