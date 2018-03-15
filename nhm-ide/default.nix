# needs NIX_ALLOW_INSECURE=1 for webkitgtk
with import <nixpkgs> {};

stdenv.mkDerivation {
   name = "nhm-ide";
   src = ./cse.nhm.ide.build/target/products/cse.nhm.ide.application-linux.gtk.x86_64.zip;

   # pkgs.fetchurl {
   #    url = http://deccnhm.org.uk/standalone/cse.nhm.ide.application-linux.gtk.x86_64.zip;
   #    sha256 = "05dx9w3d5ls7339fk9w34q4zh8mnq3f0fp66arg2vnv9d3bcvz52";
   # };

   phases = [ "installPhase" ];
   buildInputs = [ makeWrapper ];

   installPhase = ''
   mkdir -p "$out/opt/nhm-ide"
   exe="$out/opt/nhm-ide/national-household-model"

    ${pkgs.unzip}/bin/unzip $src -d "$out/opt/nhm-ide"
    mv "$out/opt/nhm-ide/National Household Model" "$exe"
    patchelf --set-interpreter $(cat $NIX_CC/nix-support/dynamic-linker) "$exe"
    patchelf --set-rpath "${pkgs.freetype}/lib:${pkgs.fontconfig}/lib:${pkgs.xorg.libX11}/lib:${pkgs.xorg.libXrender}/lib:${pkgs.zlib}/lib" "$exe"

    wrapProgram "$exe" --prefix LD_LIBRARY_PATH : "${pkgs.glib}/lib:${pkgs.gnome2.gtk.out}/lib:${pkgs.xorg.libXtst}/lib:${webkitgtk}/lib"
   '';
}
