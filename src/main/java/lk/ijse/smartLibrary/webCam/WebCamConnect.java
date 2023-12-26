package lk.ijse.smartLibrary.webCam;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class WebCamConnect {
    public static void webCamUse () throws IOException {
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        ImageIO.write(webcam.getImage(), "JPG", new File("firstCapture.jpg"));
        webcam.close();
    }
}


