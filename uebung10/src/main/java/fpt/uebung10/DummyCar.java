package fpt.uebung10;

public class DummyCar {
	private final String model;

	public DummyCar(String model) {
		this.model = model;
	}

	public String getModel() {
		return model;
	}

	@Override
	public String toString() {
		return String.format("DummyCar{model='%s'}", model);
	}
}
