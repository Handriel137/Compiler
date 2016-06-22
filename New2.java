abstract class Visitor{
	
	
	//generic one.  Job is to farm out to one of the specific ones.
	void visit(Node n)
	{
		if node.class.name=="A"
		visitA(n);
		
		else if node.class.name =="B"
			visitB(n)
			
		else if node.class.name =="C"
			visitC(n);
	
	}
	
	visitA(A Node)
	{
		print node.a;
		for(int i=0; i < node.children.length)
			node.children[i].apply(this);
		
	}
	
	visitB(B Node){
		
		print node.b;
		for(int i=0; i < node.children.length)
			node.children[i].apply(this);
	}
	
	}
	
	visitC(C Node){
		
		print node.c;
		for(int i=0; i < node.children.length)
			node.children[i].apply(this);
	}
	
	
}

class A extends Node{
	int a;
	
	
	void Apply(Visitor V)
	{
		V.visitA(this);
	}
	}
}

abstract class Node
{
	Node[] children;

	void Apply( Visitor v)
	{
		v.visit(this);
	}
	
	
}




Apply(V v){
	v.VisitA(this);
}