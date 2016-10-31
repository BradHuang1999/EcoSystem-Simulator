
public class Main {

	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();

		Fieldd field = new Fieldd(100, 0.3, 0.3, 0.3, 0.01, 0.01, 10, 10, 10);
		DisplayGrid grid = new DisplayGrid(field.getFieldGrid());
		grid.refresh();

		int turnNum = 0;

		while (!field.isExtinct()) {
			field.turn(50);
			try {
				Thread.sleep(50);
			} catch (Exception e) {
			}
			grid.refresh();
			turnNum++;
		}

		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("\nThe system survived " + turnNum + " turns.\nElapsed time = " + elapsedTime + "\nAverage " + (int)(Math.round((double)elapsedTime/(double)turnNum)) + " miliseconds per turn.");
	}

}
