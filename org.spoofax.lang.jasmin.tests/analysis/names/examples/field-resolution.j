.class public simplefieldclazz
.super java/lang/Object

; Field that is refered
.field private fieldz I
; duplicate field
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
    ; Reference to a field
    putfield simplefieldclazz/fieldz I
    
    aload_0
    getfield simplefieldclazz/fieldz I
    
    ireturn
  .end method