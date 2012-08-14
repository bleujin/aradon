package net.ion.radon.core.routing;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.util.WrapperList;

public final class SectionRouteList extends WrapperList<SectionRoute> {
	private volatile int lastIndex;

	public SectionRouteList() {
		super(new CopyOnWriteArrayList<SectionRoute>());
		this.lastIndex = -1;
	}

	public SectionRouteList(List<SectionRoute> delegate) {
		super(new CopyOnWriteArrayList<SectionRoute>(delegate));
		this.lastIndex = -1;
	}

	public SectionRoute getBest(Request request, Response response, float requiredScore) {
		SectionRoute result = null;
		float bestScore = 0F;
		float score;

		for (SectionRoute current : this) {
			score = current.score(request, response);

			if ((score > bestScore) && (score >= requiredScore)) {
				bestScore = score;
				result = current;
			}
		}

		return result;
	}

	public SectionRoute getFirst(Request request, Response response, float requiredScore) {
		for (SectionRoute current : this) {
			if (current.score(request, response) >= requiredScore) {
				return current;
			}
		}

		// No match found
		return null;
	}

	public synchronized SectionRoute getLast(Request request, Response response, float requiredScore) {
		for (int j = size() - 1; (j >= 0); j--) {
			final SectionRoute route = get(j);
			if (route.score(request, response) >= requiredScore) {
				return route;
			}
		}

		// No match found
		return null;
	}

	public synchronized SectionRoute getNext(Request request, Response response, float requiredScore) {
		if (!isEmpty()) {
			for (final int initialIndex = this.lastIndex++; initialIndex != this.lastIndex; this.lastIndex++) {
				if (this.lastIndex >= size()) {
					this.lastIndex = 0;
				}

				final SectionRoute route = get(this.lastIndex);
				if (route.score(request, response) >= requiredScore) {
					return route;
				}
			}
		}

		// No match found
		return null;
	}

	public synchronized SectionRoute getRandom(Request request, Response response, float requiredScore) {
		int length = size();

		if (length > 0) {
			int j = new Random().nextInt(length);
			SectionRoute route = get(j);

			if (route.score(request, response) >= requiredScore) {
				return route;
			}

			boolean loopedAround = false;

			do {
				if ((j == length) && (loopedAround == false)) {
					j = 0;
					loopedAround = true;
				}

				route = get(j++);

				if (route.score(request, response) >= requiredScore) {
					return route;
				}
			} while ((j < length) || !loopedAround);
		}

		// No match found
		return null;
	}

	public synchronized void removeAll(Restlet target) {
		for (int i = size() - 1; i >= 0; i--) {
			if (get(i).getNext() == target) {
				remove(i);
			}
		}
	}

	@Override
	public SectionRouteList subList(int fromIndex, int toIndex) {
		return new SectionRouteList(getDelegate().subList(fromIndex, toIndex));
	}
}
