    import javax.sound.sampled.*; 
    import java.util.ArrayList;
    import java.io.File; import java.io.IOException; 
    
    public class SoundManagerTwo { 

        private ArrayList<String> filePaths; 
        private Clip clip; 
        
        public SoundManagerTwo(ArrayList<String> filePaths) { 
            this.filePaths = filePaths; 
        } 
        
        public void playSound(int index) {
             if (index < 0 || index >= filePaths.size()) { 
                System.out.println("Invalid index."); 
                return; 
            } 
            //quits trying to play if the index is invalid
            stop(); 

            try { File soundFile = new File(filePaths.get(index));
                 AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile); 
                 clip = AudioSystem.getClip(); 
                 clip.open(audioInputStream); 
                 clip.start(); 
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) 
                { e.printStackTrace(); 
            } } 
            
            public void stop() {
                 if (clip != null && clip.isRunning()) {
                    clip.stop(); 
                } } 
                
                public void rewind() { 
                    if (clip != null) { clip.setMicrosecondPosition(0);
             } } }
