package Game;


public class Physics {
	static Collision tempC = new Collision();
	final static float T_EPSILON = 0.005f;
	
	public static float getDistance(float x1, float y1, float x2, float y2){
		float xdist = x1 - x2;
		float ydist = y1 - y2;
		return (float)Math.sqrt(xdist*xdist + ydist*ydist);
	}
	
	// checks if a player will collide with the side of the window during the given time step
    public static void CheckBoundaryCollision(float x, float y, float width, float height, float xvol, float yvol, float time, Collision c)
    {
        // for each wall, find the first collision with that wall and save it if its an earlier collision than
        // any found so far

        // left
        CheckVerticalCollision(x, width / 2, xvol, 0, time);
        if (tempC.t <= c.t)
        {
            c.copy(tempC);
        }
        // right
        CheckVerticalCollision(x, width / 2, xvol, MainWindow.DIMS.width, time);
        if (tempC.t <= c.t)
        {
            c.copy(tempC);
        }
        // up
        CheckHorizontalCollision(y, height / 2, yvol, 0, time);
        if (tempC.t <= c.t)
        {
            c.copy(tempC);
        }
        // down
        CheckHorizontalCollision(y, height / 2, yvol, MainWindow.DIMS.height, time);
        if (tempC.t <= c.t)
        {
            c.copy(tempC);
        }
    }

    // checks collisions with a vertical line
    public static void CheckVerticalCollision(float x, float radius, float xvol, float linex, float time)
    {
        // reset temporary collision holder
        tempC.reset();
        // no collision if player not moving
        if (xvol == 0)
        {
            return;
        }

        // find distance from the wall being checked
        float distance;
        if (linex > x)
        {
            distance = linex - (x + radius);
        }
        else
        {
            distance = linex - x + radius;
        }
        // time until player collides with the wall
        float timeToCollision = distance / xvol;

        // if the collision occurs in the given time step, save it in the temp collision
        if (timeToCollision > 0 && timeToCollision <= time)
        {
            tempC.xcollide = true; // mark which axis collision occurs on
            tempC.t = timeToCollision; // save time of the collision
        }

    }

    // checks collisions with a horizontal line, see above for commenting as its the same code for different axis
    public static void CheckHorizontalCollision(float y, float radius, float yvol, float liney, float time)
    {
        tempC.reset();
        if (yvol == 0)
        {
            return;
        }

        float distance;
        if (liney > y)
        {
            distance = liney - (y + radius);
        }
        else
        {
            distance = liney - y + radius;
        }

        float timeToCollision = distance / yvol;

        if (timeToCollision > 0 && timeToCollision <= time)
        {
            tempC.ycollide = true;
            tempC.t = timeToCollision;
        }

    }

}
