 import javax.sound.sampled.*; 
    import java.util.ArrayList;
    import java.io.File; import java.io.IOException; 
    
    public class SoundManager { 

        private ArrayList<String> filePaths; 
        private Clip clip; 
        
        public SoundManager() { 
        this.filePaths.add("sound/gameOver.wav");
        this.filePaths.add("sound/Ambiance.wav");
        this.filePaths.add("sound/correctAnswer.wav");
        this.filePaths.add("sound/disappointment.wav");
        this.filePaths.add("sound/wrongAnswer.wav");
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
