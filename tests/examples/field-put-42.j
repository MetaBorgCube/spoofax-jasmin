.class public simplefieldclazz
.super java/lang/Object

.field private fieldz I

	.method public <init>()V
	   aload_0
	
	   invokenonvirtual java/lang/Object/<init>()V
	   return
	.end method
  
  .method public method()I
    .limit stack 2
    
    aload_0
    ldc 42
    putfield simplefieldclazz/fieldz I
    
    aload_0
    getfield simplefieldclazz/fieldz I
    
    ireturn
  .end method