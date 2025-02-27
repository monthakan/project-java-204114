package Main;

public class Score {

	GameManager gm;
	public Score(GameManager gm) {this.gm = gm;}
	
	private int[] list_itemA = {0, 0, 0};
	private int[] list_itemB = {0, 0, 0};
	private int orderA=0, orderB=0, score=0, s;

	
	public void setListItemA(int item) {

		list_itemA[orderA] = item;
		orderA++;
		
		if(orderA == list_itemA.length) {
			
			orderA = 0;
		}
	}
	
	public void resetListItemA() {

		for(int i=0;i<list_itemA.length;i++) {
			
			list_itemA[i] = 0;
		}
	}
	
	public void setListItemB(int item) {

		list_itemB[orderB] = item;
		orderB++;
		
		if(orderB == list_itemB.length) {
			
			orderB = 0;
		}
	}
	
	public void resetListItemB() {

		for(int i=0;i<list_itemB.length;i++) {
			
			list_itemB[i] = 0;
		}
	}
	
	//public void setRecipe(String[] recipe) { this.recipe = recipe;}
	
	public int getScore() {
		
		System.out.println("SCORE" + ": "+ score);
		return score;
	}
	
	public void resetScore() {
		
		score =0;
	}
	
	public void calScoreA() { 
		
		int [] recipe = {1, 1, 0}; // test case
		
		for(int i=0;i<list_itemA.length;i++) {
			
			s = Math.abs(list_itemA[i]-recipe[i]);
			System.out.println(list_itemA[i]+" | "+recipe[i] + " = " + s);
			if(s == 0) {
				switch(i) { //
			
				case 0: score += (list_itemA[i] * 10); break;
				case 1: score += (list_itemA[i]* 20); break;
				case 2: score += (list_itemA[i] * 30); break;
				}
			} else {
				switch(i) {
					case 0: score -= (s * 10); break;
					case 1: score -= (s * 20); break;
					case 2: score -= (s * 30); break;
				}
			}
			System.out.println(score);
		}
		resetListItemA();
	}
	
	public void calScoreB() {
		
		int [] recipe = {1, 1, 0}; // test case
		
		for(int i=0;i<list_itemB.length;i++) {
			
			s = Math.abs(list_itemB[i]-recipe[i]);
			System.out.println(list_itemB[i]+" | "+recipe[i] + " = " + s);
			if(s == 0) {

				switch(i) {
			
					case 0: score += (list_itemB[i] * 10); break;
					case 1: score += (list_itemB[i] * 20); break;
					case 2: score += (list_itemB[i] * 30); break;
				}
			} else {
				
				switch(i) {
				
					case 0: score -= (s * 10); break;
					case 1: score -= (s * 20); break;
					case 2: score -= (s * 30); break;
				}
			}
			System.out.println(score);
		}
		resetListItemB();
	}
}