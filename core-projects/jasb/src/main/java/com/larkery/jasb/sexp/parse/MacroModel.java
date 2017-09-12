package com.larkery.jasb.sexp.parse;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

public class MacroModel {
	public final String description;
	public final ImmutableMap<String, String> allowedKeys;
	public final ImmutableMap<String, String> requiredKeys;
	public final ImmutableMap<String, String> allKeys;
	public final ImmutableList<String> allowedPos;
	public final ImmutableList<String> requiredPos;
	public final Optional<String> restNum;

	public MacroModel(final String description,
			final ImmutableMap<String, String> allowedKeys,
			final ImmutableMap<String, String> requiredKeys,
			final ImmutableList<String> allowedPos,
			final ImmutableList<String> requiredPos,
			final Optional<String> restNum) {
				this.description = description;
				this.allowedKeys = allowedKeys;
				this.requiredKeys = requiredKeys;
				this.allowedPos = allowedPos;
				this.requiredPos = requiredPos;
				this.restNum = restNum;
				this.allKeys = ImmutableMap.<String, String>builder()
					.putAll(requiredKeys)
					.putAll(allowedKeys)
					.build();
	}

	public Optional<String> descriptionAndArguments() {
		final StringBuffer sb = new StringBuffer();
		if (description != null) {
			sb.append(description);
			sb.append("\n\n");
		}
		for (final Map.Entry<String, String> e : requiredKeys.entrySet()) {
			sb.append(String.format("%s: (required) %s\n\n", e.getKey(), e.getValue()));
		}
		for (final Map.Entry<String, String> e : allowedKeys.entrySet()) {
			sb.append(String.format("%s: (optional) %s\n\n", e.getKey(), e.getValue()));
		}
		int i = 1;
		for (final String p : requiredPos) {
			sb.append(String.format("Unnamed argument %d: (required) %s\n", i, p));
			i++;
		}
		for (final String p : allowedPos) {
			sb.append(String.format("Unnamed argument %d: (optional) %s\n", i, p));
			i++;
		}
		if (restNum.isPresent()) {
			sb.append(String.format("Remaining arguments: %s", restNum.get()));
		}

		if (sb.length() > 0) {
			return Optional.of(sb.toString());
		} else {
			return Optional.absent();
		}
	}

	public static class Builder {
		final StringBuffer description = new StringBuffer();
		final ImmutableMap.Builder<String, String> requiredKeys = ImmutableMap.builder();
		final ImmutableMap.Builder<String, String> allowedKeys = ImmutableMap.builder();
		final ImmutableList.Builder<String> requiredNums = ImmutableList.builder();
		final ImmutableList.Builder<String> allowedNums = ImmutableList.builder();
		Optional<String> restNum = Optional.absent();

		public Builder desc(final String desc) {
			description.append(desc + " ");
			return this;
		}

		public class Keys {
			public Builder and() {
				return Builder.this;
			}

			public Keys allow(final String key, final String description) {
				allowedKeys.put(key, description);
				return this;
			}

			public Keys require(final String key, final String description) {
				requiredKeys.put(key, description);
				return this;
			}
		}

		public class Pos {
			public Builder and() {
				return Builder.this;
			}

			public Pos require(final String description) {
				requiredNums.add(description);
				return this;
			}

			public Pos allow(final String description) {
				allowedNums.add(description);
				return this;
			}

			public Pos remainder(final String description) {
				restNum = Optional.of(description);
				return this;
			}
		}

		public Pos pos() {
			return new Pos();
		}

		public Keys keys() {
			return new Keys();
		}

		public MacroModel build() {
			return new MacroModel(description.toString(), allowedKeys.build(), requiredKeys.build(), allowedNums.build(), requiredNums.build(), restNum);
		}
	}

	public static MacroModel.Builder builder() {
		return new Builder();
	}
}
