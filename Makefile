
build:
	cd ./application && ./build-script.sh
	docker-compose build
run:
	docker-compose up
