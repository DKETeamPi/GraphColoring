import java.io.File;
import javax.sound.sampled .AudioSystem;
import javax.sound.sampled.Clip;

 public class Sound extends Thread
{
  //This method will be executed when this thread is executed
  public void run()
  {

    //Looping from 1 to 10 to display numbers from 1 to 10
      File video = new File("video.wav");
      PlaySound(video);

   }
   static void PlaySound(File Sound){
   	   try{
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(Sound));
        clip.start();
        Thread.sleep(clip.getMicrosecondLength()/1000);

}
     catch (Exception e) {

     }
    }
  }
