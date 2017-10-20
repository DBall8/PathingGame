package Game;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player {
	
	private static final float MAX_SPEED = 5;
	
	private float x, y;
	private float width = 60;
	private float height = 60;
	private float xvol, yvol;
	
	private KeyPressHandler keys = null;
	
	Collision earliestCollision = new Collision(); // earliest
    Collision tempCollision = new Collision(); // holds a potential earliest collision

	public Player(int startx, int starty) {
		xvol = yvol = 0;
		x = startx;
		y = starty;
	}
	
	public void draw(Graphics2D g2){
		g2.setPaint(Color.BLUE);
		g2.fillRoundRect((int)(x-width/2), (int)(y-height/2), (int)width, (int)height, 20, 20);
	}
	
	public void setKeys(KeyPressHandler k){
		keys = k;
	}
	
	public void reset()
    {
        earliestCollision.reset();
        tempCollision.reset();
    }

    // Check for a collision with the game world boundaries in the given time step
    public float CheckBoundaryCollision(float time)
    {
        // check for collisions
        Physics.CheckBoundaryCollision(x, y, width, height, xvol, yvol, time, tempCollision);
        // save collision if earlier than earliest detected collision
        if (tempCollision.t <= earliestCollision.t)
        {
            earliestCollision.copy(tempCollision);
            return earliestCollision.t;
        }
        return time;
    }
    
    // move player a certain amount of time forward
    public void move(float time)
    {
        // If earliest collision occurs during time step
        if(earliestCollision.t <= time)
        {
            // wipe velocity along axis on which collision occurs
            if (earliestCollision.xcollide)
            {
                xvol = 0;
            }
            if (earliestCollision.ycollide)
            {
                yvol = 0;
            }
            // move player to spot at which collision occurs
            x = earliestCollision.getNewX(x, xvol);
            y = earliestCollision.getNewY(y, yvol);
        }
        else // otherwise just move player according to velocities
        {
            x += xvol * time;
            y += yvol * time;
        }
        
    }
	
	public void update(){
		if(keys == null){
			System.out.println("NO KEYS");
			return;
		}
		
		if(keys.up && !keys.down){
			yvol = -MAX_SPEED;
		}
		else if(!keys.up && keys.down){
			yvol = MAX_SPEED;
		}
		else{
			yvol = 0;
		}
		
		if(keys.right && !keys.left){
			xvol = MAX_SPEED;
		}
		else if(!keys.right && keys.left){
			xvol = -MAX_SPEED;
		}
		else{
			xvol = 0;
		}
		
	}

}
