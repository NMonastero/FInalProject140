package projectview;
import java.util.Observable;
import project.MachineModel;
import javax.swing.JFrame;
public class ViewMediator extends Observable{
	public void step() { }
	private MachineModel model;
	private JFrame frame;
	public MachineModel getModel() {
		return model;
	}
	public void setModel(MachineModel model) {
		this.model = model;
	}
	public JFrame getFrame() {
		return frame;
	}
}
