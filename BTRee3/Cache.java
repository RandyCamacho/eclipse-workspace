
public class Cache<T>
{
	private Cache_Level<T> one;
	private int hitnum1; //how many times first cache hit
	
	public Cache(int x)
	{
		hitnum1 = 0;
		one = new Cache_Level<T>(x);
		System.out.println("Cache one created with size " + one.getSize());
			
		if(one == null)
		{
			System.out.println("Cache not initialized");
		}
		
	}
		
	public void cacheAdd(T O)
	{
		if(O == null)
		{
			System.out.println("NULL OBJECT");
		}
		else if( O != null ) 
		{
			if(one == null)
			{
				System.out.println("Cache not initialized");
			}
			one.addObject(O);
			hitnum1 = one.getHit();
		}
	}
	public int GETREF1()
	{
		int temp;
		temp = one.getReference();
		return temp;
	}

	
	public int GETHIT1()
	{
		return hitnum1;
	}

	public void cacheResults() 
	{
		int refs = 0;
		int tHits = 0;
		double gHitRatio = 0;
		int L1Hits = 0;
		double L1HitRatio = 0;
		

		
		refs = one.getReference(); //Number of references
		tHits = one.getHit(); //number of total Hits
		gHitRatio = ((double) tHits) / ((double) refs); // global hit ratio
		L1Hits = one.getHit();
		L1HitRatio = ((double) L1Hits / ((double) refs)); // ratio for L1
		
		System.out.println("Total number of references: " + refs);
		System.out.println("Total number of cache hits: " + tHits);
		System.out.println("The global hit ratio                  : " + gHitRatio );
		System.out.println("Number of 1st-level cache hits: " + L1Hits);
		System.out.println("1st-level cache hit ratio             : " + L1HitRatio);
	}
}