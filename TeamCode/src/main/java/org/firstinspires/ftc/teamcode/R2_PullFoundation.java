package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class R2_PullFoundation extends R_2_OpMode
{
    @Override
    public void runOpMode() {

        initRobot(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        waitForStart();
       // runtime.reset();

        //set encoder
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // rightArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //rightArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //leftArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //leftArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            if (opModeIsActive()) {

                /* Move 12 inches forward */
                driveStraight(.5, 12 );

                //beginning of strafe to the left assuming 12 inches as encoderDrivingTarget is already set to 12
                strafeLeft( 0.5,30);

                /* Move 15 inches forward */
                driveStraight(.5, 15 );

                //grabbing foundation using servo
                grabFoundation();

                // beginning of moving back, 27 inches is needed
                driveStraight(-.5, 27 );

                //releasing foundation using servos
                releaseFoundation();

                //rest for 19 seconds
                sleep(19000);

                //move right for parking
                strafeRight(.5, 35);


        }
    }
}
