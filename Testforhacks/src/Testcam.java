
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import java.util.Timer;

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
        
        //long startTime = System.currentTimeMillis();

        //callOperationToTime();

        //long endTime   = System.currentTimeMillis();

        //long totalTime = endTime - startTime;
        int check = 0;
        int check2 = 0;
        long startTime = 0;
        long endTime = 0;
        Mat sight = null;

        
		while (true) {
			
			vcam.read(m);
			//System.out.println(m.dump());
			if(check == 0){
			im.showImage(m);
			}
	        MatOfRect faces = new MatOfRect();

	        face_cascade.detectMultiScale(m, faces, 1.1, 2, 0, new Size(50,50), new Size() );
	        
	        
	        Rect[] facesArray = faces.toArray();
	        if(facesArray.length !=0 && check ==0){
	        	check = 1;
	        	
	        }
	        if(im.getBreak() == true&& check2 == 0){
	        	check2 = 1;
	        	startTime = System.currentTimeMillis();
	        }
	        endTime = System.currentTimeMillis();
	        if(facesArray.length !=0 ){
	        	
	        	Mat grey_m = new Mat(m.rows(), m.cols(), CvType.CV_32F);
	    		Imgproc.cvtColor(m, grey_m, Imgproc.COLOR_BGRA2GRAY);
	    		for(int i = 0; i <grey_m.rows(); i++){
	    			for(int j = 0; j <grey_m.cols(); j++){
	    				if(grey_m.get(i, j)[0] >105){
	    					double[] data = {255};
	    					grey_m.put(i, j, data);
	    				}
	    			}
	    		}
	    		sight = Highgui.imread(im.getfile());
	    		for(int i = 0; i < grey_m.rows() ;i++){
	    			for(int j = 0; j <grey_m.cols(); j++){
	    				if(grey_m.get(i, j)[0] <255){
	    					double[] data = m.get(i, j);
	    					sight.put(i, j, data);
	    				}
	    			}
	    		}
	    		
	    		im.showImage(sight);
	    		
	        }else{
	        	check = 0;
	        	startTime = 0;
	        	endTime = 0 ;
	        }
	        if(endTime - startTime >3000  && im.getBreak() == true){
	        	break;
	        }
		}
		
		
		
		Highgui.imwrite("Test_cam.jpg", sight);
		
		
		vcam.release();
	}
	
	
}