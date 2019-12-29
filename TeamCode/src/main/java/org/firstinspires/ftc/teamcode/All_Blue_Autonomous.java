package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="All_Blue_Autonomous", group="zzz")
public class All_Blue_Autonomous extends R_2_OpMode
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

     //   releaseFoundation();

    //    rotate (1,13.2);

     //   driveStraight(.5, 69 );

        //rotate (-1,13.2);




        sleep(200);

        //grabbing foundation using servo

        //move forward to hit foundation
        driveStraight(0.25, 3 );

        //grab foundation
        grabFoundation();

        sleep(1000);

        //rotate to put foundation in base
        rotate(.5,-12);

        //move back 22 inches
        driveStraight(0.25, -15 );

        //rotate to put foundation in base
        rotate(1,-16);

        releaseFoundation();

        //align foundation to the wall
        driveStraight(0.25, 27.5 );


        //move back 10 inches
      //  driveStraight(0.25, 4 );

        //turn facing forwards
//        rotate(.5,10);

        //move forward 5 inches
//        driveStraight(0.25, -5 );

        //turn towards the skystone
//        rotate(.5,-10);

        //move towards the skystones
//        driveStraight(0.25, -5 );






        sleep(500);





        //releasing foundation using servos

        //Just move 1 inch forward so that robot won't touch the glass on back side
       // driveStraight(.25, 1 );

        break;
    }
}
}