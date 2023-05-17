package view.components;

import javax.swing.JTextField;

import utils.Readable;

/**
 * La classe {@code ReadableTextField} permet de creer un {@code TextField} qui implemente l'interface {@code Readable}.
 */
public class ReadableTextField extends JTextField implements Readable {

	private static final long serialVersionUID = 1L;

	public ReadableTextField() {
        super();
    }
	
	
    
    @Override
    public String readData() {
        return this.getText();
    }



	@Override
	public boolean isEmpty() {
		return this.getText().isEmpty();
	}


	@Override
	public void addEventListener(FunctionalPanel<?> funPanel) {
		this.getDocument().addDocumentListener(funPanel);
		
	}



	@Override
	public void removeEventListener(FunctionalPanel<?> funPanel) {
		this.getDocument().removeDocumentListener(funPanel);
		
	}



	@Override
	public void clearData() {
		this.setText("");
		
	}
    
}
