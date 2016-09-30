/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.ScreenManagement;

/**
 *
 * @author i3mainz
 */
public class VDriver
{
    String name;
    double oldAct;
    double start,act,goal;
    double timeToGo,time;
    boolean play;
    double tolerance;
    boolean easeIn,easeOut;
    
    public VDriver(String name)
    {
        this.name = name;
        tolerance = 0.01;
        play = false;
        easeIn = true;
        easeOut = true;
    }
    public void setGoal(double from,double to,double time)
    {
        setGoal(from,to,time,true,true);
    }
    public void setGoal(double from,double to,double time,boolean easeIn,boolean easeOut)
    {
        this.start = from;
        this.oldAct = from;
        this.act = from;
        this.goal = to;
        this.timeToGo = time;
        this.time = 0;
        this.easeIn = easeIn;
        this.easeOut = easeOut;
        if(timeToGo==0)
            act = goal;
        else
            this.play = true;
    }
    
    public void update()
    {
        if(!play)
            return;
        if(easeIn && easeOut)
            act = move_expo_in_out(time,start,goal-start,timeToGo);
        if(easeIn && !easeOut)
            act = move_expo_in(time,start,goal-start,timeToGo);
        if(!easeIn && easeOut)
            act = move_expo_out(time,start,goal-start,timeToGo);
        
        time += 1;
        if(Math.round(act) == Math.round(goal))
            play = false;
        oldAct = act;
    }
    public double getValue()
    {
        return act;
    }
    public boolean isDone()
    {
        return !play;
    }
    //t: current time
    //b: start value
    //c: changeInValue
    //d: duration
    public double move_linear(double t,double b,double c,double d)
    {
        return c*t/d + b;
    }
    public double move_expo_in_out(double t,double b,double c,double d)
    {
	t /= d/2;
	if (t < 1) return c/2 * Math.pow( 2, 10 * (t - 1) ) + b;
	t--;
	return c/2 * ( -Math.pow( 2, -10 * t) + 2 ) + b;
    }
    public double move_expo_in(double t,double b,double c,double d)
    {
	return c * Math.pow( 2, 10 * (t/d - 1) ) + b;
    }
    public double move_expo_out(double t,double b,double c,double d)
    {
	return c * ( -Math.pow( 2, -10 * t/d ) + 1 ) + b;
    }
}
