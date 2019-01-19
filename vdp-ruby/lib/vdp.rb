require "vdp/version"

module Vdp
  # Configuration 
  @config = {}

  # Configure through hash
  def self.configure(opts = {})
    opts.each {|k,v| @config[k.to_sym] = v}
  end

  # Configure through yaml file
  def self.configure_with()
    begin
      config = YAML::load(IO.read('credentials.yml'))
    rescue Errno::ENOENT
      log(:warning, "YAML configuration file couldn't be found. Using defaults."); 
      return
    rescue Psych::SyntaxError
      log(:warning, "YAML configuration file contains invalid syntax. Using defaults."); 
      return
    end
    configure(config)
  end 

  def self.config
    @config
  end 
end
