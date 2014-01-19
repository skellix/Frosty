import java.util.HashMap;


public class GetVariable extends FrostCommand {

	private String text;

	public GetVariable(String text) {
		this.text = text;
	}

	@Override
	public void execute(FrostThread frostThread) {
		String[] args = text.split("\\.");
		Object fc = null;
		if (frostThread.variables.containsKey(args[0])) {
			fc = frostThread.variables.get(args[0]);
		} else {
			frostThread.error("No variable named '"+args[0]+"' has been declared in this scope");
		}
		for (int i = 1 ; i < args.length-1 ; i ++) {
			String key = args[i];
			if (fc instanceof HashMap<?, ?>) {
				HashMap<?, ?> map = (HashMap<?, ?>) fc;
				if (map.containsKey(key)) {
					fc = map.get(key);
				} else {
					frostThread.error("hashmap '"+args[i-1]+"' does not contain '"+key+"'");
				}
			} else {
				frostThread.error(args[i-1]+"is not a hashmap");
			}
		}
		frostThread.carry.set(fc);
	}

	public String getInitString() {
		return this.getClass().getSimpleName()+"(\""+text+"\")";
	}
}