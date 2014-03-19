class JZypp 
{
  public native String whatProvides(String packageName);
  static 
  {
    System.loadLibrary("ZyppImpl"); 
  }
}

