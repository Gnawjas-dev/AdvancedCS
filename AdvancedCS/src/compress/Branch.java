package compress;

public class Branch <E> {
	
		E info;
		boolean isLeaf;
		Branch<E> left, right;
		
		public Branch(E info) {
			
			this.info = info;
			this.isLeaf = true;
			
		}
		
		public Branch(Branch<E> left, Branch<E> right) {
			this.isLeaf = false;
			this.left = left;
			this.right = right;
		}	
		
		public E getInfo() {
			return info;
		}
		
		public String toString() {
			if(isLeaf)
				return info.toString();
			else
				return "not a leaf";
		}

}
