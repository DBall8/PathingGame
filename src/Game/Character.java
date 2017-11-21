package Game;

import java.awt.Graphics2D;

public abstract class Character {
	
	private static final float TINY = 0.005f;
	
	protected static final float MAX_SPEED = 5;
	
	protected float x, y;
	protected float width = 60;
	protected float height = 60;
	protected float xvol, yvol;
	
	Collision earliestCollision = new Collision(); // earliest
    Collision tempCollision = new Collision(); // holds a potential earliest collision

	public Character(int startx, int starty) {
		xvol = yvol = 0;
		x = startx;
		y = starty;
	}
	
	public Character(){
		xvol = yvol = 0;
		x = 200;
		y = 200;
	}
	
	
	public abstract void draw(Graphics2D g2);
	
	public void reset()
    {
        earliestCollision.reset();
        tempCollision.reset();
    }

    // Check for a collision with the game world boundaries in the given time step
    public float CheckBoundaryCollision(float time)
    {
        // check for collisions
        Physics.checkBoundaryCollision(x, y, width, height, xvol, yvol, time, tempCollision);
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
        	
        	// move player to spot at which collision occurs
            x = earliestCollision.getNewX(x, xvol);
            y = earliestCollision.getNewY(y, yvol);
            // wipe velocity along axis on which collision occurs
            if (earliestCollision.xcollide)
            {
                xvol = 0;
            }
            if (earliestCollision.ycollide)
            {
                yvol = 0;
            }
            
        }
        else // otherwise just move player according to velocities
        {
            x += xvol * time;
            y += yvol * time;
        }
        
    }
	
	public void update(){	
	}
	
	public float checkObstacleCollision(float time, Obstacle o){
		float earliestTime = time;
		float newx;
		float newy;
		boolean insideY;
		boolean insideX;
		
		if(o.health <=0 ){
			return time;
		}
		// NOTE for the future, its probably best to modify physics functions so that I can pass
		// my tempC to be mutated and not use the Physics tempC
		Physics.checkVerticalCollision(x, width/2, xvol, o.xpoints[0], time);
		newy = (yvol)*Physics.tempC.t + y;
		insideY =  newy < o.ypoints[1] + height/2 - TINY && newy > o.ypoints[0] - height/2 + TINY;
		if(Physics.tempC.t < earliestCollision.t && insideY){
			earliestCollision.copy(Physics.tempC);
			earliestTime = Physics.tempC.t;
		}
		
		Physics.checkVerticalCollision(x, width/2, xvol, o.xpoints[1], time);
		newy = (yvol)*Physics.tempC.t + y;
		insideY =  newy < o.ypoints[1] + height/2 - TINY && newy > o.ypoints[0] - height/2 + TINY;
		if(Physics.tempC.t < earliestCollision.t && insideY){
			earliestCollision.copy(Physics.tempC);
			earliestTime = Physics.tempC.t;
		}

		Physics.checkHorizontalCollision(y, height/2, yvol, o.ypoints[0], time);
		newx = (xvol)*Physics.tempC.t + x;
		insideX =  newx < o.xpoints[1] + width/2 - TINY && newx > o.xpoints[0] - width/2 + TINY;
		if(Physics.tempC.t < earliestCollision.t && insideX){
			earliestCollision.copy(Physics.tempC);
			earliestTime = Physics.tempC.t;
		}

		Physics.checkHorizontalCollision(y, height/2, yvol, o.ypoints[1], time);
		newx = (xvol)*Physics.tempC.t + x;
		insideX =  newx < o.xpoints[1] + width/2 - TINY && newx > o.xpoints[0] - width/2 + TINY;
		if(Physics.tempC.t < earliestCollision.t && insideX){
			earliestCollision.copy(Physics.tempC);
			earliestTime = Physics.tempC.t;
		}


		return earliestTime;
	}


}
