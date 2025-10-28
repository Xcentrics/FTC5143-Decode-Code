package org.firstinspires.ftc.teamcode.xcentrics.pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class ball extends OpenCvPipeline {

    // --- Tuning Constants ---

    // Define the HSV color ranges for purple and green.
    // IMPORTANT: These values are a starting point. You will need to tune them
    // for your specific lighting conditions and ball colors.
    private final Scalar lowerPurple = new Scalar(120, 40, 50);
    private final Scalar upperPurple = new Scalar(155, 255, 255);
    private final Scalar lowerGreen = new Scalar(40, 50, 50);
    private final Scalar upperGreen = new Scalar(85, 255, 255);

    // Minimum area for a contour to be considered a ball (in pixels).
    // This helps filter out noise. Tune this value based on camera resolution
    // and how far the balls are.
    private final double MIN_CONTOUR_AREA = 250;

    // --- Internal Processing Mats ---
    private final Mat hsvMat = new Mat();
    private final Mat purpleMask = new Mat();
    private final Mat greenMask = new Mat();
    // Kernel for morphological operations
    private final Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));

    // --- Output Data ---

    // A list to store the bounding rectangles of all detected purple balls.
    // 'volatile' ensures that the OpMode thread can safely access this data
    // while the pipeline thread is updating it.
    private volatile List<Rect> purpleBallRects = new ArrayList<>();

    // A list to store the bounding rectangles of all detected green balls.
    private volatile List<Rect> greenBallRects = new ArrayList<>();


    /**
     * This method is called by the EasyOpenCV library on every camera frame.
     * It processes the image to detect the balls.
     *
     * @param input The camera frame in RGB format.
     * @return The processed frame with detections drawn on it.
     */
    @Override
    public Mat processFrame(Mat input) {
        // 1. Convert the input frame from RGB to the HSV color space.
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);

        // 2. Create binary masks for each color.
        Core.inRange(hsvMat, lowerPurple, upperPurple, purpleMask);
        Core.inRange(hsvMat, lowerGreen, upperGreen, greenMask);

        // 3. Clean up the masks to reduce noise.
        // Erode and Dilate (Opening) helps remove small blobs.
        // Dilate and Erode (Closing) helps fill gaps in larger blobs.
        Imgproc.morphologyEx(purpleMask, purpleMask, Imgproc.MORPH_OPEN, kernel);
        Imgproc.morphologyEx(purpleMask, purpleMask, Imgproc.MORPH_CLOSE, kernel);
        Imgproc.morphologyEx(greenMask, greenMask, Imgproc.MORPH_OPEN, kernel);
        Imgproc.morphologyEx(greenMask, greenMask, Imgproc.MORPH_CLOSE, kernel);

        // 4. Find contours and filter them for each color.
        // We create new lists here to hold the results of the current frame.
        List<Rect> newPurpleRects = findFilteredContours(purpleMask);
        List<Rect> newGreenRects = findFilteredContours(greenMask);

        // 5. Atomically update the volatile lists that the OpMode will access.
        // This prevents concurrency issues (e.g., the OpMode reading a list while it's being modified).
        purpleBallRects = newPurpleRects;
        greenBallRects = newGreenRects;

        // 6. Draw the bounding rectangles onto the output image for visualization.
        drawRects(input, purpleBallRects, new Scalar(255, 0, 255)); // Magenta for purple
        drawRects(input, greenBallRects, new Scalar(0, 255, 0));   // Green for green

        // Return the final image to be displayed on the Driver Station.
        return input;
    }

    /**
     * Finds all contours in a binary mask, filters them by area,
     * and returns a list of their bounding rectangles.
     *
     * @param mask The binary mask to process.
     * @return A List of Rect objects for the filtered contours.
     */
    private List<Rect> findFilteredContours(Mat mask) {
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();

        // Find all contours. RETR_EXTERNAL is good for finding outer boundaries.
        Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        List<Rect> rects = new ArrayList<>();
        // Iterate through all found contours.
        for (MatOfPoint contour : contours) {
            // If a contour is large enough, add its bounding box to the list.
            if (Imgproc.contourArea(contour) > MIN_CONTOUR_AREA) {
                rects.add(Imgproc.boundingRect(contour));
            }
        }
        return rects;
    }

    /**
     * Helper method to draw a list of rectangles onto an image.
     * @param image The image to draw on.
     * @param rects The list of rectangles to draw.
     * @param color The color to draw the rectangles.
     */
    private void drawRects(Mat image, List<Rect> rects, Scalar color) {
        for (Rect rect : rects) {
            Imgproc.rectangle(image, rect, color, 2);
        }
    }


    // --- Public Accessor Methods ---
    // These are the methods you will call from your OpMode to get the detection results.

    /**
     * Returns a list of all detected purple ball bounding rectangles.
     * This list may be empty if no purple balls are found.
     *
     * @return A new ArrayList containing the Rects of detected purple balls.
     */
    public List<Rect> getPurpleBallRects() {
        // Return a copy to prevent modification errors while iterating.
        return new ArrayList<>(purpleBallRects);
    }

    /**
     * Returns a list of all detected green ball bounding rectangles.
     * This list may be empty if no green balls are found.
     *
     * @return A new ArrayList containing the Rects of detected green balls.
     */
    public List<Rect> getGreenBallRects() {
        // Return a copy to prevent modification errors while iterating.
        return new ArrayList<>(greenBallRects);
    }
}
