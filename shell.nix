{pkgs ? import <nixpkgs> {}}:
pkgs.mkShell {
  name = "java-shell";
  packages = with pkgs; [
    openjdk
    gradle
    jdt-language-server
  ];
  VIM_EXTRA_PATH = with pkgs;
  with vimPlugins.nvim-treesitter.builtGrammars;
    builtins.concatStringsSep ":" [
      (neovimUtils.grammarToPlugin java)
    ];
}
