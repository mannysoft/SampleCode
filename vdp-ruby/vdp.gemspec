# coding: utf-8
lib = File.expand_path('../lib', __FILE__)
$LOAD_PATH.unshift(lib) unless $LOAD_PATH.include?(lib)
require 'vdp/version'

Gem::Specification.new do |spec|
  spec.name          = "vdp"
  spec.version       = Vdp::VERSION
  spec.authors       = ["Mradul Modi"]
  spec.email         = ["mmodi@visa.com"]
  spec.summary       = 'Ruby sample code for Visa api'
  spec.description   = 'The power of visa network delivered as an API. For more details refer to https://vdp.visa.com'
  spec.homepage      = "https://vdp.visa.com"
  spec.license       = "TODO"

  spec.files         = `git ls-files -z`.split("\x0")
  spec.executables   = spec.files.grep(%r{^bin/}) { |f| File.basename(f) }
  spec.test_files    = spec.files.grep(%r{^(test|spec|features)/})
  spec.require_paths = ["lib"]

  spec.add_development_dependency "bundler", "~> 1.7"
  spec.add_development_dependency "rake", "~> 10.0"
end
