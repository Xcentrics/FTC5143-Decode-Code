
public class auto extends OpMode{
    private Camera camera;
    private Pose currentPose; // Current pose of the robot
    private Follower follower; // Pedro Pathing follower
    private TelemetryManager panelsTelemetry; // Panels telemetry


    // Initialize Panels telemetry
        
        // Initialize Pedro Pathing follower
        
        boolean targetFound = false;    // Set to true when an AprilTag target is detected
   
    @Override
    init(){
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
        camera = new Camera(hardwareMap, "camera");
    }

    @Override
    loop(){
        follower.update();
        camera.perodic();
        
    }
}