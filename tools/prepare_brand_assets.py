from collections import deque
from pathlib import Path
from PIL import Image


ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "app" / "src" / "main" / "res" / "drawable-nodpi"
LOGO_SOURCE = Path(r"C:\Users\Santi\Downloads\ChatGPT Image 7 sept. 2026, 23_05_44 (1).png")
PIGEON_SOURCE = Path(
    r"C:\Users\Santi\.codex\generated_images\01a07d2f-39b3-7b90-8a62-5af50de0a5fa\exec-fd2a66e9-db91-4736-bc83-8ab92576b573.png"
)


def prepare_logo() -> None:
    image = Image.open(LOGO_SOURCE).convert("RGBA")
    # The supplied PNG contains scattered alpha=1 export noise far outside the
    # visible wordmark. Ignore only that imperceptible fringe when cropping.
    visible_alpha = image.getchannel("A").point(lambda alpha: 255 if alpha >= 2 else 0)
    bounds = visible_alpha.getbbox()
    if bounds is None:
        raise ValueError("The logo source is empty")
    image = image.crop(bounds)
    max_width = 900
    if image.width > max_width:
        height = round(image.height * max_width / image.width)
        image = image.resize((max_width, height), Image.Resampling.LANCZOS)
    padded = Image.new("RGBA", (image.width + 32, image.height + 32))
    padded.alpha_composite(image, (16, 16))
    padded.save(OUTPUT / "exci_logo.png", optimize=True)


def connected_light_background(image: Image.Image) -> Image.Image:
    rgb = image.convert("RGB")
    width, height = rgb.size
    pixels = rgb.load()
    background = bytearray(width * height)
    queue: deque[tuple[int, int]] = deque()

    def is_background(x: int, y: int) -> bool:
        red, green, blue = pixels[x, y]
        return min(red, green, blue) >= 214 and max(red, green, blue) - min(red, green, blue) <= 16

    def add(x: int, y: int) -> None:
        index = y * width + x
        if not background[index] and is_background(x, y):
            background[index] = 1
            queue.append((x, y))

    for x in range(width):
        add(x, 0)
        add(x, height - 1)
    for y in range(height):
        add(0, y)
        add(width - 1, y)

    while queue:
        x, y = queue.popleft()
        if x > 0:
            add(x - 1, y)
        if x + 1 < width:
            add(x + 1, y)
        if y > 0:
            add(x, y - 1)
        if y + 1 < height:
            add(x, y + 1)

    rgba = rgb.convert("RGBA")
    alpha = Image.new("L", (width, height), 255)
    alpha_pixels = alpha.load()
    for y in range(height):
        row = y * width
        for x in range(width):
            if background[row + x]:
                alpha_pixels[x, y] = 0
    rgba.putalpha(alpha)
    return rgba


def prepare_pigeon_frames() -> None:
    sprite = connected_light_background(Image.open(PIGEON_SOURCE))
    frames: list[Image.Image] = []
    boxes: list[tuple[int, int, int, int]] = []
    for index in range(7):
        left = round(index * sprite.width / 7)
        right = round((index + 1) * sprite.width / 7)
        frame = sprite.crop((left, 0, right, sprite.height))
        bounds = frame.getbbox()
        if bounds is None:
            raise ValueError(f"Pigeon frame {index + 1} is empty")
        frames.append(frame.crop(bounds))
        boxes.append(bounds)

    max_width = max(frame.width for frame in frames)
    max_height = max(frame.height for frame in frames)
    scale = min(340 / max_width, 340 / max_height)

    for index, frame in enumerate(frames, start=1):
        size = (round(frame.width * scale), round(frame.height * scale))
        resized = frame.resize(size, Image.Resampling.LANCZOS)
        canvas = Image.new("RGBA", (384, 384))
        x = (canvas.width - resized.width) // 2
        y = 370 - resized.height
        canvas.alpha_composite(resized, (x, y))
        canvas.save(OUTPUT / f"pigeon_frame_{index}.png", optimize=True)


if __name__ == "__main__":
    OUTPUT.mkdir(parents=True, exist_ok=True)
    prepare_logo()
    prepare_pigeon_frames()
