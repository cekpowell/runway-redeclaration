package IO;

/**
 * Wrapper class, for generic objects, this is so you can safely serialize/deserialize, as you can only read objects seq which would require to keep track of number of objects within xml files
 */
public class Wrapper<T> {
    Class<T> wrappedClass;
    T[] wrappedObj;
    public Wrapper(){}
    public Wrapper(Class<T> classvar){
        this.wrappedClass = classvar;
    }
    public Class<T> getWrappedClass() {
        return this.wrappedClass;
    }

    public void setWrappedClass(Class<T> wrappedClass) {
        this.wrappedClass = wrappedClass;
    }


    public T[] getWrappedObj() {
        return wrappedObj;
    }

    public void setWrappedObj(T[] wrappedObj) {
        this.wrappedObj = wrappedObj;
    }

}
