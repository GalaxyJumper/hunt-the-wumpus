import 
public class Sound implements Playable {

    private final Path wavPath;
    private final CyclicBarrier barrier = new CyclicBarrier(2);

    Sound(final Path wavPath) {

        this.wavPath = wavPath;
    }

    @Override
    public void play() throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        try (final AudioInputStream audioIn = AudioSystem.getAudioInputStream(wavPath.toFile());
             final Clip clip = AudioSystem.getClip()) {

            listenForEndOf(clip);
            clip.open(audioIn);
            clip.start();
            waitForSoundEnd();
        }
    }

    private void listenForEndOf(final Clip clip) {

        clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) waitOnBarrier();
        });
    }

    private void waitOnBarrier() {

        try {

            barrier.await();
        } catch (final InterruptedException ignored) {
        } catch (final BrokenBarrierException e) {

            throw new RuntimeException(e);
        }
    }

    private void waitForSoundEnd() {

        waitOnBarrier();
    }
}
