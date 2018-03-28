## use as
## NIX_PATH='nixpkgs=https://nixos.org/channels/nixos-17.03/nixexprs.tar.xz' nix-shell user-environment.nix
## to get a shell with all the build and test dependencies in it
## then you can run build.sh

with (import <nixpkgs> {});

stdenv.mkDerivation rec {
  name = "nhm-build-env";
  env = buildEnv {name = name; paths = buildInputs;};
  buildInputs = [
    git xmlstarlet jdk maven gradle webfs
    (rWrapper.override {
      packages = with rPackages;
      [ dplyr jsonlite stringr data_table plyr ggplot2 tidyr ];
    })
  ];
}
