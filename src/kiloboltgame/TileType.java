package kiloboltgame;

public enum TileType implements IEntity {
	OCEAN(0), 
	DIRT(5),
	GRASS_TOP(8),
	GRASS_BOTTOM(2),
	GRASS_LEFT(4),
	GRASS_RIGHT(6);
	
	private int index;
	
	private TileType(int index) {
		this.index = index;
	}
	
	public static TileType get(int index) {		
		TileType type = null;
		
		for (TileType value : values()) {
			if (value.index == index) {
				type = value;
				break;
			}
		}		
		return type;		
	}
}
