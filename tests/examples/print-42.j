.class public Simple
.super java/lang/Object

.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

.method public m()I
	ldc 42
	ireturn
.end method

.method public static main([Ljava/lang/String;)V
    new Simple
    dup
	invokenonvirtual Simple/<init>()V
	invokevirtual Simple/m()V
    return
.end method

.method public m()V
	getstatic java/lang/System/out Ljava/io/PrintStream;
	ldc 42 	
	invokevirtual java/io/PrintStream/println(I)V
	return
.end method