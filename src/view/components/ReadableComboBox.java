package view.components;

import javax.swing.JComboBox;

import utils.Listable;
import utils.Readable;
/**
 * La classe {@code ReadableComboBox} permet de creer un {@code ComboBox} qui implemente l'interface {@code Readable}.
 */
public class ReadableComboBox extends JComboBox<String> implements Readable {

		private static final long serialVersionUID = 1L;
		Listable data;

		public ReadableComboBox() {
            super();
        }

        public void setModel(Listable dataModel) {
            super.setModel(new ListableModel(dataModel));
           
        }
        
        
        

        @Override
        public String readData() {
     
            return this.getSelectedItem().toString();
        }

		@Override
		public boolean isEmpty() {
			return this.getSelectedItem() == null;
		}

		@Override
		public void addEventListener(FunctionalPanel<?> funPanel) {
			this.addActionListener(funPanel);

		}

		@Override
		public void removeEventListener(FunctionalPanel<?> funPanel) {
			
			this.removeActionListener(funPanel);
			
		}

		@Override
		public void clearData() {
			this.setSelectedItem("");
			
		}
    
}
