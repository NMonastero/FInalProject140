package projectview;
import javax.swing.Timer;

public class Animator {
	static final int TICK = 500;
	 boolean autoStepOn = false;
	 Timer timer;
	 ViewMediator view;
	 public Animator(ViewMediator v) {
		 view = v;
	 }
	 public boolean isAutoStepOn() {
		 return autoStepOn;
	 }
	 public void setAutoStepOn(boolean b) {
		 autoStepOn = b;
	 }
	 public void toggleAutoStep() {
		 if(autoStepOn==false) {
			 autoStepOn = true;
		 }
		 else if(autoStepOn==true) {
			 autoStepOn = false;
		 }
	 }
	 public void setPeriod(int period) {
		 timer.setDelay(period);
	 }
	 public void start() {
			timer = new Timer(TICK, e -> {if(autoStepOn) view.step();});
			timer.start();
	 }
	 
}
