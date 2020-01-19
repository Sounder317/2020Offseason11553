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

            // Move 2 inches forward
            driveStraight(.5, 2 );

            //beginning of strafe to the right for 23 inches
            strafeLeft( 0.25,23);

            // Move 23 inches forward
            driveStraight(.5, 19 );
            driveStraight(.25, 5 );

            sleep(2000);

            //grabbing foundation using servo
            grabFoundation();
            sleep(1000);

            // beginning of moving back, 30 inches is needed
            driveStraight(0.25, -31 );
            //driveStraight(0.25, -6 );
            sleep(500);

            //releasing foundation using servos
            releaseFoundation();
            sleep(1000);

            //Just move 1 inch forward so that robot won't touch the glass on back side
            driveStraight(0.25, 1 );
            sleep(1000);

            //Sleep for 5 more seconds
            sleep(5000);

            //move left for parking
            strafeRight(0.5, 40);

            //be aligned against the wall
            driveStraight(0.5, -4 );

            break;
        }
    }
}