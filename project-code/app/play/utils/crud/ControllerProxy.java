package play.utils.crud;

import play.mvc.Controller;
import play.mvc.Result;
import play.utils.meta.ModelMetadata;
import play.utils.meta.convert.Converter;

import java.io.Serializable;

public abstract class ControllerProxy<M, K extends Serializable> extends Controller {

	protected CRUD<M, K> delegate;
	protected ModelMetadata model;
	protected Converter<K> keyConverter;

	@SuppressWarnings("unchecked")
	public ControllerProxy(CRUD<M, K> delegate, ModelMetadata model) {
		this.delegate = delegate;
		this.model = model;
		this.keyConverter = (Converter<K>) model.getKeyConverter();
	}

	public Result list() {
		return delegate.list();
	}

	public Result create() {
		return delegate.create();
	}

	public Result show(String key) {
		K k = keyConverter.convert(key);
		return delegate.read(k);
	}

	public Result update(String key) {
		K k = keyConverter.convert(key);
		return delegate.update(k);
	}

	public Result delete(String key) {
		K k = keyConverter.convert(key);
		return delegate.delete(k);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ControllerProxy(" + model.getName() + ") [").append(delegate).append("]");
		return builder.toString();
	}
}
