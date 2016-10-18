package uk.org.cse.nhm.spss.codegen;

public interface TypeDescriptor {
	final TypeDescriptor STRING = new TypeDescriptor() {
		public String getPackage() {
			return "java.lang";
		}

		public String getName() {
			return "String";
		}

		public void use() {
			
		}
	};
	
	final TypeDescriptor INTEGER = new TypeDescriptor() {
		public String getPackage() {
			return "java.lang";
		}

		public String getName() {
			return "Integer";
		}

		public void use() {
			
		}
	};

	final TypeDescriptor DOUBLE = new TypeDescriptor() {
		public String getPackage() {
			return "java.lang";
		}

		public String getName() {
			return "Double";
		}

		public void use() {
			
		}
	};		
	
	public String getPackage();
	public String getName();
	public void use();
}
