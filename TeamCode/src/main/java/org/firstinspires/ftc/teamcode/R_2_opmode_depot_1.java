package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import android.graphics.Color;

import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Depot_opmode", group ="ZZZ")
//@Disabled
public class R_2_opmode_depot_1 extends R_2_OpMode {

 //public double leftArmMotorPosition = 0;
 //public double rightArmMotorPosition= 0;
// create a timer
    protected ElapsedTime runtime = new ElapsedTime();
    
    @Override public void runOpMode() 
    {
       
        initRobot(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    
        telemetry.addData("Status", "Initialized");
        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        
        waitForStart();
        runtime.reset(); 
      
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
        
        if (opModeIsActive())
        {
            //rightArmMotorPosition = rightArmMotor.getCurrentPosition();
           // telemetry.addData("rightArmMotor is ", rightArmMotorPosition);
           // leftArmMotorPosition=leftArmMotor.getCurrentPosition();
           // telemetry.addData("leftArmMotor is", rightArmMotorPosition);
           LandOffLander();
            GetOffLander();
            GotoCrater();
        }
    }

    public void LandOffLander()
    {
        telemetry.addData(">", "Landing");
        telemetry.update();

       // rightArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //leftArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        //rightArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //leftArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        //int rightArmMotorPosition = rightArmMotor.getCurrentPosition();
        //telemetry.addData("right Position", rightArmMotorPosition);
        //rightArmMotor.setTargetPosition(3000);
        //rightArmMotor.setPower(1);
        //int leftArmMotorPosition = leftArmMotor.getCurrentPosition();
        //telemetry.addData("left Position", leftArmMotorPosition);
        //leftArmMotor.setTargetPosition(3000);
        //leftArmMotor.setPower(1);
        //while(leftArmMotor.isBusy() && rightArmMotor.isBusy())
        //{
          //  telemetry.addData("Left Arm is ", leftArmMotor.getCurrentPosition());
           // telemetry.addData("Right Arm is ", rightArmMotor.getCurrentPosition());
            //telemetry.update();
        //}
    }
    
    public void GotoCrater()
    {
        driveStraight(0.5,28.0);
        rotate(0.5,35);
        driveStraight(1.0,-22.0);
         telemetry.addData(">", "Claiming");
        telemetry.update();

       // rightArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //leftArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
    //    rightArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      //  leftArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        //int rightArmMotorPosition = rightArmMotor.getCurrentPosition();
        //telemetry.addData("right Position", rightArmMotorPosition);
        //rightArmMotor.setTargetPosition(4000);
        //rightArmMotor.setPower(1);
        //int leftArmMotorPosition = leftArmMotor.getCurrentPosition();
        //telemetry.addData("left Position", leftArmMotorPosition);
        //leftArmMotor.setTargetPosition(4000);
        //leftArmMotor.setPower(1);
        //while(leftArmMotor.isBusy() && rightArmMotor.isBusy())
        //{
          //  telemetry.addData("Left Arm is ", leftArmMotor.getCurrentPosition());
           // telemetry.addData("Right Arm is ", rightArmMotor.getCurrentPosition());
            //telemetry.update();
        //}
        sleep(2000);
        
        rotate(0.5,-24);
        driveStraight(1.0,-95.0);
        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
    }
    
    public void GetOffLander()
    {
        //rightArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //leftArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        //rightArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //leftArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        //int rightArmMotorPosition = rightArmMotor.getCurrentPosition();
        //telemetry.addData("right Position", rightArmMotorPosition);
        //rightArmMotor.setTargetPosition(100);
        //rightArmMotor.setPower(1);
        //int leftArmMotorPosition = leftArmMotor.getCurrentPosition();
        //telemetry.addData("left Position", leftArmMotorPosition);
        //leftArmMotor.setTargetPosition(100);
        //leftArmMotor.setPower(1);
        //while(leftArmMotor.isBusy() && rightArmMotor.isBusy())
        //{
          //  telemetry.addData("Left Arm is ", leftArmMotor.getCurrentPosition());
            //telemetry.addData("Right Arm is ", rightArmMotor.getCurrentPosition());
            //telemetry.update();
        //}
        sleep(2000);
        strafeRight(0.5, 5);
        sleep(500);
        driveStraight(0.5,-2.5);
    }

}