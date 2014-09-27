
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

public class Testcam {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		VideoCapture vcam = new VideoCapture(0);
		while (vcam.isOpened() == false)
			;

		Imshow im = new Imshow("Video Preview", vcam);

			im.Window.setResizable(true);
		// -------------------------
		Mat m = new Mat();
		while (m.empty()) {
			vcam.retrieve(m);
			
		}
		String face_cascade_name = "haarcascade_frontalface_alt.xml";
		CascadeClassifier face_cascade = new CascadeClassifier();
        if(!face_cascade.load(face_cascade_name))
        {
            System.out.println("Error loading face cascade");
        }
        else
        {
            System.out.println("Success loading face cascade");
        }
        
		

		while (true) {
			
			vcam.read(m);
			//System.out.println(m.dump());
			im.showImage(m);
	        MatOfRect faces = new MatOfRect();

	        face_cascade.detectMultiScale(m, faces, 1.1, 2, 0, new Size(200,200), new Size() );
	        Rect[] facesArray = faces.toArray();
			if(facesArray.length != 0){
				break;
			}
		}
		im.showImage(m);
		Highgui.imwrite("Test_cam.jpg", m);
		vcam.release();
	}

}