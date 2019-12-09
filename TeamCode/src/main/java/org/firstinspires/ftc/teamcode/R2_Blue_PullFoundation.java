package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="R2_Blue_PullFoundation", group="zzz")
public class R2_Blue_PullFoundation extends R_2_OpMode
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

        while (opModeIsActive()) {

            releaseFoundation();

            // Move 12 inches forward
            driveStraight(.5, 20 );
            driveStraight(.25, 5 );

            //beginning of strafe to the left assuming 12 inches as encoderDrivingTarget is already set to 12
            //strafeLeft( 0.5,30);
            sleep(2000);

            //grabbing foundation using servo
            grabFoundation();
            sleep(1000);

            // beginning of moving back, 27 inches is needed
            driveStraight(0.25, -5 );
            sleep(500);

            driveStraight(0.25, -5 );
            sleep(500);

            driveStraight(0.25, -5 );
            sleep(500);

            driveStraight(0.25, -5 );
            sleep(500);

            //semiReleaseFoundation();

            driveStraight(0.25, -6 );
            sleep(500);

            //releasing foundation using servos
            releaseFoundation();
            sleep(1000);

            //move Right for parking
            strafeRight(.5, 35);

            break;
        }
    }
}