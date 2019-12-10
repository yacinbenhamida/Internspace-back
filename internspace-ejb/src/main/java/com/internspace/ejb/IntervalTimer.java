package com.internspace.ejb;


import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

@Singleton
@LocalBean
@Startup
public class IntervalTimer {
	@Resource
    private TimerService timerService;
 
    @PostConstruct
    private void init() {
    	/*long duration = 6000;
    	Timer timer =
    	    timerService.createSingleActionTimer(duration, new TimerConfig());*/
    	timerService.createIntervalTimer(10, 50000, new TimerConfig(null, false));
    	//50000 = 50 seconds

    }
  
    @Timeout
    public void execute(Timer timer) {
        //System.out.println("Timer Service : " + ((javax.ejb.Timer) timer).getInfo());
        //System.out.println("Sending Notification Current Time : " + new Date());
        //System.out.println("Next Timeout : " + ((javax.ejb.Timer) timer).getNextTimeout());
        //System.out.println("Time Remaining : " + ((javax.ejb.Timer) timer).getTimeRemaining());
        //System.out.println("____________________________________________");
        //timer.cancel();
    }
    
    
}
