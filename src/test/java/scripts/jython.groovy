import org.python.util.*;
import org.python.core.*;
p = new PythonInterpreter();
p.execfile("__init__.py");
d = p.eval("DefaultCircumstance");
d.__getattr__("setParameter").__call__(new PyString("test"),Py.java2py(1))